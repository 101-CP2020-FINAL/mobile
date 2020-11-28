package hz.hz.atomchat.news

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
import hz.hz.atomchat.tasks.TaskTime
import hz.hz.atomchat.tasks.taskView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@ExperimentalCoroutinesApi
class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.epoxy_fragment, container, false)

        Timber.d("news")

        val model: NewsViewModel by viewModels()

        val rv = view.findViewById<EpoxyRecyclerView>(R.id.rv)

        model.tasks().observe(view) {
            Timber.d("observe news")

            diff(LCEState<State>::content) {
                Timber.d("diff news")

                if (it.content == null) {
                    return@diff
                }

                rv.withModels {
                    header {
                        id("header1")
                        title(
                            view.context.getString(
                                R.string.task_header_in_progress,
                                it.content.news.size
                            )
                        )
                    }

                    for (task in it.content.news) {
                        taskView {
                            id(task.id)
                            title(task.title)
                            priority(task.priority)
                            action(task.action)
                            time(TaskTime(task.startTime, task.endTime))
                        }
                    }
                }
            }
        }

        return view
    }
}
