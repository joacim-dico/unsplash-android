package com.niden.unsplash

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.niden.unsplash.PhotoDetail.PhotoDetailFragment
import com.niden.unsplash.PhotosGrid.PhotoViewModel
import com.niden.unsplash.PhotosGrid.PhotosGridFragment

class MainActivity : AppCompatActivity(), FragNavController.RootFragmentListener {

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager, R.id.contentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the root before initializing the state
        fragNavController.rootFragmentListener = this
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)

    }

    override val numberOfRootFragments: Int = 1

    override fun getRootFragment(index: Int): Fragment {
        return PhotosGridFragment()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.let { fragNavController.onSaveInstanceState(it) }

    }

    override fun onBackPressed() {
        if(!fragNavController.popFragment()) {
            super.onBackPressed()
        }
    }

    fun pushDetailView(photo: PhotoViewModel) {
        fragNavController.pushFragment(PhotoDetailFragment(photo))
    }

}
