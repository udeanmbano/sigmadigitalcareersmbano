package com.mbano.eventsapp.ui.video

import android.app.ProgressDialog
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mbano.eventsapp.R
import com.mbano.eventsapp.databinding.FragmentVideoBinding
import kotlinx.android.synthetic.main.fragment_video.view.*


class VideoFragment : Fragment(R.layout.fragment_video)
{
    // Your Video URL
    private var videoUrl= ""

    private val binding by viewBinding(FragmentVideoBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setHasOptionsMenu(true);
       videoUrl=arguments?.getString("link").toString()
     playVideo()
    }
  private fun playVideo(){

      val videoView: VideoView = binding.root.videoView


      val bufferingDialog: ProgressDialog = ProgressDialog.show(
          context,
          "Buffering", "Please wait", true, true
      )

      val uri: Uri = Uri.parse(videoUrl)

      videoView.setVideoURI(uri)

      val mediaController = MediaController(requireContext())

      mediaController.setAnchorView(videoView)

      mediaController.setMediaPlayer(videoView)

      videoView.setMediaController(mediaController)


      videoView.start()


      videoView.setOnPreparedListener { mp ->
          mp.setOnInfoListener { mp, what, extra ->
              if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) bufferingDialog.show()
              if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) bufferingDialog.dismiss()
              false
          }
      }
      videoView.setOnErrorListener { mp, what, extra ->
          bufferingDialog.dismiss()
          false
      }


  }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        //do something with your id
        return super.onOptionsItemSelected(item)
    }

}