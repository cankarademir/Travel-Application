package com.cankarademir.travelapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cankarademir.travelapplication.databinding.FragmentHomeBinding
import com.cankarademir.travelapplication.models.TravelData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnAdd = binding.btnAdd

        btnAdd.setOnClickListener { view ->

            var title = binding.editTextTitle.text.toString()
            var location = binding.editTextLocation.text.toString()
            var note = binding.editTextNote.text.toString()
            var user = Firebase.auth.currentUser?.uid

            val dt = TravelData(title, location, note)

            val db = FirebaseDatabase.getInstance().getReference("user").child("$user")
            val travelid = db.push().key

            if (title.isNotEmpty() && location.isNotEmpty() && note.isNotEmpty()) {
                db.child("$travelid").setValue(dt).addOnCompleteListener {
                    Snackbar.make(view, "Save Succesful", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()

                }.addOnFailureListener {
                    Snackbar.make(view, "Save Failed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            } else {
                Snackbar.make(view, "Please fill in the blanks completely ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}