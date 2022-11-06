package com.example.xmlapi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.example.xmlapi.databinding.FragmentCafeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CafeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CafeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentCafeBinding
    private lateinit var database: DatabaseReference
    private lateinit var arr:ArrayList<Cafe>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCafeBinding.inflate(inflater,container)
        val listView = binding.listView
        arr=ArrayList<Cafe>()
        database = Firebase.database.reference
        val adapter = CafeAdapter(requireContext(),arr)
        binding.listView.adapter=adapter
        val listener = ListClickListener()
        binding.listView.onItemClickListener=listener
        database.child("cafe").addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(snap in snapshot.children){

                        //val cafeName:String = snap.getValue<String>()!!
                        val reviewNums:Int = snap.child("totalCount").getValue<Int>()!!
                        val reviewStars:Float = snap.child("totalScore").getValue<Float>()!!
                        val name:String = snap.child("name").getValue<String>()!!
                        arr.add(Cafe(name,reviewNums,reviewStars))
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
        )



        return binding.root
    }
    inner class ListClickListener: AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            (activity as RealActivity).activateCafeInformActivity(arr[position].cafeName)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener() {

//        val btnSequence = binding.container.children
//        btnSequence.forEach { btn ->
//            btn.setOnClickListener(this)
//        }
    }

    override fun onClick(v: View) {
        //(activity as RealActivity).activateCafeInformActivity(binding.btnBan.text.toString())

    }

    companion object {
        private const val TAG = "MainFragment"
        fun instance() = CafeFragment()
    }
}