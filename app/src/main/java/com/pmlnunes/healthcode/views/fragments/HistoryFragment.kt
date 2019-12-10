package com.pmlnunes.healthcode.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.pmlnunes.healthcode.FragmentListener
import com.pmlnunes.healthcode.views.adapters.HistoryAdapter
import com.pmlnunes.healthcode.R
import com.pmlnunes.healthcode.models.Product
import com.pmlnunes.healthcode.views.activities.PREFERENCE_FILE_KEY
import com.pmlnunes.healthcode.views.activities.PRODUCTS_SCANNED
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.core.content.ContextCompat
import com.pmlnunes.healthcode.VerticalSpaceItemDecoration


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
class HistoryFragment(val callback:FragmentListener) : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var myAdapter: HistoryAdapter? = null
    private var products: MutableList<Product>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        loadProducts()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        myAdapter = HistoryAdapter(products!!, context!!, callback)
        recyclerView = view?.findViewById(R.id.recView)


        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = myAdapter

        val itemDecorator = VerticalSpaceItemDecoration(20)
        recyclerView?.addItemDecoration(itemDecorator)

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
            PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)


        var prodArray = sharedPref!!.getString(PRODUCTS_SCANNED, "no_history")


        var productList = mutableListOf<Product>()

        if(prodArray !== "no_history"){
            productList = Klaxon().parseArray<Product>(prodArray!!)!!.toMutableList()
        }

        products = productList


    }

}

