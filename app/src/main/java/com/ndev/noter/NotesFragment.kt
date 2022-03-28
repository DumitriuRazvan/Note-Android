package com.ndev.noter

import android.os.Bundle
import android.view.*
//import android.widget.SearchView
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class NotesFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null


    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter


    /// Declaram doua lista pentru recyclerview, una pentru display si una ca surse de adevar in care nu vom altera datele la search
    private var noteList = mutableListOf<String>()
    private var displayList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


        /// Umplem notitele cu date mock de test din functia NoteUtil()
        val util = NoteUtil()
        noteList= util.getDemonotes()

        if(displayList.isEmpty())
        {
            displayList.addAll(noteList)
        }


        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /// Apelam recyclerview-ul cu datele din displayList
        var notesView = inflater.inflate(R.layout.fragment_notes, container, false)

        recyclerView = notesView.findViewById(R.id.recycleView)
        recyclerAdapter = RecyclerAdapter(displayList)
        recyclerView.adapter = recyclerAdapter

        return notesView
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)


        var item: MenuItem = menu.findItem(R.id.action_search)

        //Handle-ul search-ul

        /// Daca avem elemente
        if(item !=null)
        {
            var searchView = item.actionView as SearchView

            /// Ascultam dupa input
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                // Daca se schimba textul
                override fun onQueryTextChange(newText: String?): Boolean {

                    if(newText!!.isNotEmpty())
                    {
                        displayList.clear()
                        var search = newText.toLowerCase(Locale.getDefault())

                        for(note in noteList)
                        {
                            if(note.toLowerCase(Locale.getDefault()).contains(search)){

                                displayList.add(note)
                            }
                            recyclerView.adapter!!.notifyDataSetChanged()

                        }
                    }else
                    {
                        displayList.clear()
                        displayList.addAll(noteList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                    return true

                }


            }

            )

        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}