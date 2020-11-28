package hz.hz.atomchat.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hz.hz.atomchat.R
import hz.hz.atomchat.commonviews.button
import hz.hz.atomchat.commonviews.header
import hz.hz.atomchat.commonviews.image
import hz.hz.atomchat.commonviews.text
import hz.hz.atomchat.render.diff
import hz.hz.atomchat.render.observe
import hz.hz.atomchat.tasks.taskView
import kotlinx.coroutines.ExperimentalCoroutinesApi

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

        model.task().observe(view) {
            diff({ it }) { task ->

                rv.withModels {
                    taskView {
                        id(task.id)
                        title(task.title)
                        priority(task.priority)
                        action(task.action)
                        endTime(task.endTime)
                    }

                    header {
                        id("details")
                        title("Описание")
                    }

                    text {
                        id("description")
                        text(task.description)
                    }

                    image {
                        id("comments")
                        image(R.drawable.comments1)
                    }

                    button {
                        id("button")
                        text("Взять в работу")
                    }
                }
            }
        }
    }
}
