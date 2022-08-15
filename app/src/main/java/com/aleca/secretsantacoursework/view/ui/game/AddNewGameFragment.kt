package com.aleca.secretsantacoursework.view.ui.game

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aleca.secretsantacoursework.R
import com.aleca.secretsantacoursework.databinding.AddNewGameFragmentBinding
import com.aleca.secretsantacoursework.model.Game
import com.aleca.secretsantacoursework.model.Pair
import com.aleca.secretsantacoursework.model.User
import com.aleca.secretsantacoursework.view.MainMenuActivity
import com.aleca.secretsantacoursework.viewmodel.AddNewGameViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

private const val ID_ARGUMENT = "ID_ARGUMENT"

class AddNewGameFragment : Fragment() {
    private lateinit var addNewGameFragmentBinding: AddNewGameFragmentBinding
    private lateinit var dateStart: String
    private lateinit var dateEnd: String
    private lateinit var listPeopleGame: ArrayList<User>
    private lateinit var listGamers: ArrayList<String>
    private lateinit var listPeoplesAll: ArrayList<User>
    private lateinit var listPair: ArrayList<Pair>
    private lateinit var listView: ListView
    var userId = 0

    private val userDetailIdArgument by lazy {
        requireArguments().getInt(ID_ARGUMENT, 0)
    }
    private lateinit var viewModel: AddNewGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[AddNewGameViewModel::class.java]
        return inflater.inflate(R.layout.add_new_game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewGameFragmentBinding = AddNewGameFragmentBinding.bind(view)
        listGamers = ArrayList()
        listPeopleGame = ArrayList()
        listPeoplesAll = ArrayList()
        actionTextViewDataEnd()
        actionTextViewDataStart()
        actionAddPeopleButton()
        actionAddGameButton()
        initList()
    }

    private fun actionTextViewDataStart() {
        addNewGameFragmentBinding.datePickerStart.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(childFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                dateStart = dateFormatter.format(Date(it))
                Toast.makeText(
                    activity?.applicationContext,
                    "$dateStart" + getString(R.string.data_is_selected),
                    Toast.LENGTH_LONG
                ).show()
                addNewGameFragmentBinding.datePickerStart.text = dateStart
            }

            // Setting up the event for when cancelled is clicked
            datePicker.addOnNegativeButtonClickListener {
                Toast.makeText(
                    activity?.applicationContext,
                    "${datePicker.headerText}" + getString(R.string.datapicker_is_closed),
                    Toast.LENGTH_LONG
                ).show()
            }

            // Setting up the event for when back button is pressed
            datePicker.addOnCancelListener {
                Toast.makeText(
                    activity?.applicationContext,
                    getString(R.string.data_picker_is_closed),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    private fun actionTextViewDataEnd() {
        addNewGameFragmentBinding.datePickerEnd.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(childFragmentManager, "DatePicker")

            // Setting up the event for when ok is clicked
            datePicker.addOnPositiveButtonClickListener {
                // formatting date in dd-mm-yyyy format.
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                dateEnd = dateFormatter.format(Date(it))
                Toast.makeText(
                    activity?.applicationContext,
                    "$dateEnd" + getString(R.string.data_is_selected),
                    Toast.LENGTH_LONG
                ).show()
                addNewGameFragmentBinding.datePickerEnd.text = dateEnd
            }

            // Setting up the event for when cancelled is clicked
            datePicker.addOnNegativeButtonClickListener {
                Toast.makeText(
                    activity?.applicationContext,
                    "${datePicker.headerText}" + getString(R.string.datapicker_is_closed),
                    Toast.LENGTH_LONG
                ).show()
            }

            // Setting up the event for when back button is pressed
            datePicker.addOnCancelListener {
                Toast.makeText(
                    activity?.applicationContext,
                    getString(R.string.data_picker_is_closed),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getPeoples(): ArrayList<String> {
        listPeoplesAll =
            activity?.applicationContext?.let { viewModel.getPeoples(it) } as ArrayList<User>
        for (i in 0 until listPeoplesAll.size) {
            listGamers.add(listPeoplesAll[i].name + " " + listPeoplesAll[i].email)
        }
        return listGamers
    }

    private fun initList() {
        listView = addNewGameFragmentBinding.list
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        listGamers = getPeoples()
        val arrayAdapter = activity?.let {
            ArrayAdapter(
                it.applicationContext,
                R.layout.item_people_in_game_small, listGamers
            )
        }
        listView.adapter = arrayAdapter
    }

    private fun getListGamers() {
        val sbArray: SparseBooleanArray = listView.checkedItemPositions
        for (i in 0 until sbArray.size()) {
            val key = sbArray.keyAt(i)
            if (sbArray[key]) {
                listPeopleGame.add(listPeoplesAll[key])
            }
        }
        if (listPeopleGame.size <= 1) {
            listPeopleGame.clear()
            Toast.makeText(
                activity?.applicationContext,
                getString(R.string.attantion_toast_count_gamers),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun actionAddPeopleButton() {
//        addNewGameFragmentBinding.addPeopleButton.setOnClickListener {
//            //дочерний фрагмент с людьми
////            val fragmentAddPeopleListFragment = newInstance()
////            childFragmentManager.beginTransaction()
////                    .addToBackStack(AddNewGameFragment.javaClass.simpleName)
////                    .replace(R.id.add_new_game, fragmentAddPeopleListFragment)
//        }
    }

    private fun actionAddGameButton() {
        addNewGameFragmentBinding.addGameButton.setOnClickListener {
            getListGamers()
            if (checkFieldsNewGame()) {
                listPair = ArrayList()
                val name = addNewGameFragmentBinding.nameGame.text.toString()
                val game = Game(
                    0,
                    name = name,
                    dateStart = dateStart,
                    dateEnd = dateEnd,
                    listPeopleGame.size
                )
                viewModel.setIdGame(game.id)
                activity?.applicationContext?.let { it1 -> viewModel.addNewGame(game, it1) }
                activity?.applicationContext?.let { it1 ->
                    viewModel.generatePairs(
                        listPeopleGame,
                        it1
                    )
                }
                activity?.applicationContext?.let { it1 ->
                    viewModel.bindGameToUsers(
                        listPeopleGame,
                        it1
                    )
                }
                val intent = Intent(activity?.baseContext, MainMenuActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    activity?.applicationContext,
                    getString(R.string.toast_dont_fill_field),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun checkFieldsNewGame(): Boolean {
        with(addNewGameFragmentBinding) {
            if (nameGame.text?.isNotEmpty() == true &&
                datePickerStart.text.isNotEmpty() &&
                datePickerEnd.text.isNotEmpty() &&
                datePickerEnd.text != datePickerStart.text
                && listPeopleGame.isNotEmpty()
            )
                return true
            return false
        }
    }

    companion object {
        fun newInstance(userID: Int) = AddNewGameFragment().apply {
            this.arguments = bundleOf(
                ID_ARGUMENT to userID
            )
            userId = userID
        }
    }
}