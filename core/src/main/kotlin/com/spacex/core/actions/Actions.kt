package com.spacex.core.actions

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.spacex.core.R

/**
 * Navigation actions for navigation between feature activities
 */
fun Fragment.openDetail(id: Long) =
    NavHostFragment.findNavController(this).navigate(Uri.parse(getString(R.string.detail_uri, id)))
