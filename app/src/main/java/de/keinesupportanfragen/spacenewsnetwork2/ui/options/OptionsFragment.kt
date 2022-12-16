package de.keinesupportanfragen.spacenewsnetwork2.ui.options

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import de.keinesupportanfragen.spacenewsnetwork2.R
/**
 * A simple [Fragment] subclass.
 * Use the [OptionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OptionsFragment : Fragment() {
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_options, container, false)

        val layoutMgrSelector: Spinner = root.findViewById(R.id.layoutmgr_select)
        ArrayAdapter.createFromResource(requireContext(), R.array.layoutmgr_selction_options, android.R.layout.simple_spinner_item)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                layoutMgrSelector.adapter = it
            }
        layoutMgrSelector.setSelection(
            (sharedPreferences?.getInt(getString(R.string.preferences_key_layoutmgr), 1)?.minus(1)) ?: 0)

        layoutMgrSelector.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                with(sharedPreferences?.edit()) {
                    this?.putInt(getString(R.string.preferences_key_layoutmgr), position + 1) ?: return
                    apply()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        return root
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = OptionsFragment()
    }
}