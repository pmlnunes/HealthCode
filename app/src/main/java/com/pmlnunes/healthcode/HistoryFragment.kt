package com.pmlnunes.healthcode

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import android.widget.AdapterView.OnItemClickListener







// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HistoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var myAdapter: HistoryAdapter? = null
    private var products: MutableList<Product>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        loadProducts()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        myAdapter = HistoryAdapter(products!!, context!!)

        myAdapter?.onItemClick = { product ->


        }

        recyclerView = view?.findViewById(R.id.recView)


        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = myAdapter

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }


    private fun loadProducts() {

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        with (sharedPref!!.edit()) {

            var prodArray = sharedPref.getString(getString(R.string.product_history), "no_history")


            var productList = mutableListOf<Product>()

            if(prodArray !== "no_history"){
                productList = Klaxon().parseArray<Product>(prodArray!!)!!.toMutableList()
            }

            products = productList

        }
    }

}

