package jetpack.randa.com.ayahandamal.activity

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import jetpack.randa.com.ayahandamal.AyahAndAmalApplication
import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.viewModel.HomeViewModel
import javax.inject.Inject
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import kotlinx.android.synthetic.main.activity_splash.*
import android.view.animation.AlphaAnimation
import java.util.*


class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeLanguage()
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splash)
            words.visibility = View.GONE
        val scaleAnim = ScaleAnimation(
            1f, 1f, // Start and end values for the X axis scaling
            0f, 1f, // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 1f
        ) // Pivot point of Y scaling
        scaleAnim.fillAfter = true // Needed to keep the result of the animation
        scaleAnim.duration = 1000
        scaleAnim.setAnimationListener(object:Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                val alphaAnim = AlphaAnimation(0f, 1.0f)
                alphaAnim.duration = 1000
                alphaAnim.fillAfter = true
                alphaAnim.setAnimationListener(object :Animation.AnimationListener{
                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                        finish()
                    }

                    override fun onAnimationStart(animation: Animation?) {
                        words.visibility = View.VISIBLE
                    }
                })
                words.startAnimation(alphaAnim)
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
        sticky.startAnimation(scaleAnim)

    }

    private fun changeLanguage() {
        val configuration = resources.configuration
        configuration.setLayoutDirection(Locale("ar"))
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
