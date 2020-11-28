package hz.hz.atomchat.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hz.hz.atomchat.R
import hz.hz.atomchat.commonviews.*
import hz.hz.atomchat.render.Loading
import hz.hz.atomchat.render.diff
import hz.hz.atomchat.render.observe
import hz.hz.atomchat.tasks.Action
import hz.hz.atomchat.tasks.TaskTime
import hz.hz.atomchat.tasks.currentTaskView
import hz.hz.atomchat.tasks.taskView
import kotlinx.coroutines.ExperimentalCoroutinesApi

fun newDetails(id: Int): DetailsFragment {
    val dialog = DetailsFragment()

    dialog.arguments = Bundle().apply { putInt("id", id) }

    return dialog
}

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@ExperimentalCoroutinesApi
class DetailsFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.epoxy_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: DetailsViewModel by viewModels()

        val rv = view.findViewById<EpoxyRecyclerView>(R.id.rv)

        model.task(requireArguments().getInt("id")).observe(view) {
            diff({ it }) { state ->

                val task = state.task

                rv.withModels {
                    if (task.action == Action.ToBeDone) {
                        currentTaskView {
                            id(task.id)
                            title(task.title)
                            priority(task.priority)
                            action(task.action)
                            endTime(task.endTime)
                        }
                    } else {
                        taskView {
                            id(task.id)
                            title(task.title)
                            priority(task.priority)
                            action(task.action)
                            time(TaskTime(task.startTime, task.endTime))
                        }
                    }


                    if (task.description.isNotBlank()) {
                        header2 {
                            id("details")
                            title("Описание")
                        }

                        text {
                            id("description")
                            text(task.description)
                        }
                    }

                    image {
                        id("comments")
                        image(R.drawable.comments1)
                    }

                    if (state.action.loading != Loading.None) {
                        progress {
                            id("progress")
                        }
                    } else {
                        button {
                            id("button")
                            text("Взять в работу")
                            clickListener(View.OnClickListener {
                                model.changeStatus()
                            })
                        }
                    }

                    if (state.action.error != null) {
                        text {
                            id("error")
                            text("Error: " + state.action.error.message)
                        }
                    }
                }
            }
        }
    }
}
