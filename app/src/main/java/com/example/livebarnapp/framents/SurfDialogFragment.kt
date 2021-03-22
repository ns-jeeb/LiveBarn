package com.example.livebarnapp.framents

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.livebarnapp.MediaActivity
import com.example.livebarnapp.R
import com.example.livebarnapp.databinding.FragmentSurfDialogBinding

private const val ARG_SURFACE_NAME = "surface"
private const val ARG_VENUE_NAME = "venue"
const val TAG = "SurfDialogFragment"

class SurfDialogFragment : DialogFragment() {

    private var surfaceName: String? = null
    private var venueName: String? = null

    private var binding : FragmentSurfDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            surfaceName = it.getString(ARG_SURFACE_NAME)
            venueName = it.getString(ARG_VENUE_NAME)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_surf_dialog, container, false)
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(surfaceName)
        builder.setMessage(venueName)
        builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {

                var intent = Intent(activity, MediaActivity::class.java)
                startActivity(intent)
                dismiss()
            }
        })
        builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dismiss()
            }
        })
        return builder.create()
    }

    companion object {

        @JvmStatic
        fun newInstance(surfaceName: String, venueName: String) =
            SurfDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SURFACE_NAME, surfaceName)
                    putString(ARG_VENUE_NAME, venueName)
                }
            }
    }
}


