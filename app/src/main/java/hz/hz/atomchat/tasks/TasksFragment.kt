package hz.hz.atomchat.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.airbnb.epoxy.EpoxyRecyclerView
import hz.hz.atomchat.R
import hz.hz.atomchat.commonviews.header
import hz.hz.atomchat.render.LCEState
import hz.hz.atomchat.render.diff
import hz.hz.atomchat.render.observe
import kotlinx.coroutines.ExperimentalCoroutinesApi

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
        return inflater.inflate(R.layout.epoxy_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: TasksViewModel by viewModels()

        val rv = view.findViewById<EpoxyRecyclerView>(R.id.rv)

        model.tasks().observe(view) {
            diff(LCEState<State>::content) {
                if (it.content == null) {
                    return@diff
                }

                rv.withModels {
                    header {
                        id("header1")
                        title(view.context.getString(R.string.task_header_in_progress, it.content.tasksInProgress.size))
                    }

                    for (task in it.content.tasksInProgress) {
                        currentTaskView {
                            id(task.id)
                            title(task.title)
                            priority(task.priority)
                            action(task.action)
                            endTime(task.endTime)
                        }
                    }

                    header {
                        id("header2")
                        title(view.context.getString(R.string.task_header_todo, it.content.tasksToDo.size))
                    }

                    for (task in it.content.tasksToDo) {
                        taskView {
                            id(task.id)
                            title(task.title)
                            priority(task.priority)
                            action(task.action)
                            endTime(task.endTime)
                        }
                    }
                }
            }
        }
    }
}
