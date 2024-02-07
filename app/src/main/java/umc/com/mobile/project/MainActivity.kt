package umc.com.mobile.project

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import umc.com.mobile.project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private lateinit var navController: NavController

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
		navController = navHostFragment.navController

		binding.navView.setupWithNavController(navController)

		binding.navView.apply {
			setupWithNavController(navController)
			setOnItemSelectedListener { item ->
				NavigationUI.onNavDestinationSelected(item, navController)
				navController.popBackStack(item.itemId, inclusive = false)
				true
			}
		}
		handleNavigationBarVisibility()
	}

	override fun onNavigateUp(): Boolean =
		navController.navigateUp() || super.onNavigateUp()


	private fun handleNavigationBarVisibility() {
		navController.addOnDestinationChangedListener { _, destination, _ ->
			binding.navView.visibility =
				when (destination.id) {
					R.id.fragment_date -> {
						View.GONE
					}

					R.id.fragment_career_add -> {
						View.GONE
					}

					else -> View.VISIBLE
				}
		}
	}
}