package hz.hz.atomchat.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.airbnb.epoxy.EpoxyRecyclerView
import hz.hz.atomchat.R
import hz.hz.atomchat.commonviews.header
import hz.hz.atomchat.commonviews.progress
import hz.hz.atomchat.commonviews.text
import hz.hz.atomchat.details.newDetails
import hz.hz.atomchat.render.Loading
import hz.hz.atomchat.render.diff
import hz.hz.atomchat.render.observe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@ExperimentalCoroutinesApi
class TasksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.epoxy_fragment, container, false)

        Timber.d("tasks")

        val model: TasksViewModel by viewModels()

        val rv = view.findViewById<EpoxyRecyclerView>(R.id.rv)

        model.tasks().observe(view) {
            diff({ it }) {
                rv.withModels {
                    if (it.loading != Loading.None) {
                        progress {
                            id("progress")
                        }
                    }

                    if (it.error != null) {
                        text {
                            id("error")
                            text("Error: " + it.error.message)
                        }
                    }

                    if (it.content == null) {
                        return@withModels
                    }

                    val size = it.content.tasksInProgress.size

                    if (size > 0) {
                        header {
                            id("header1")
                            title(
                                view.context.getString(
                                    R.string.task_header_in_progress,
                                    size
                                )
                            )
                        }

                        for (task in it.content.tasksInProgress) {
                            currentTaskView {
                                id(task.id)
                                title(task.title)
                                priority(task.priority)
                                action(task.action)
                                endTime(task.endTime)
                                clickListener(View.OnClickListener { openTask(task.id) })
                            }
                        }
                    }

                    header {
                        id("header2")
                        title(
                            view.context.getString(
                                R.string.task_header_todo,
                                it.content.tasksToDo.size
                            )
                        )
                    }

                    for (task in it.content.tasksToDo) {
                        taskView {
                            id(task.id)
                            title(task.title)
                            priority(task.priority)
                            action(task.action)
                            time(TaskTime(task.startTime, task.endTime))
                            background(true)
                            clickListener(View.OnClickListener { openTask(task.id) })
                        }
                    }
                }
            }
        }

        return view
    }

    private fun openTask(id: Int) {
        newDetails(id).show(parentFragmentManager, "bottom_fragment")
    }
}
