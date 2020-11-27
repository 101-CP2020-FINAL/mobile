package hz.hz.atomchat.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import hz.hz.atomchat.R
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
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: TasksViewModel by viewModels()

        val rv = view.findViewById<EpoxyRecyclerView>(R.id.tasks_rv)

        model.tasks().observe(view) {
            diff({ it }) {
                rv.withModels {
                    taskView {
                        id("bla")
                        title(it)
                    }
                }
            }
        }
    }
}
