package org.oy.demo.nhdztemplate.ui.fragment;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.oy.demo.nhdztemplate.R;
import org.oy.demo.nhdztemplate.vitamio.CustomMediaController;
import org.oy.chardemo.utilslibrary.windowutils.WindowUtils;

import java.lang.ref.WeakReference;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class PlayVedioFragment extends Fragment implements MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener,
        ViewTreeObserver.OnGlobalLayoutListener {
    private View view;
    private WeakReference<View> weakReference;
//    private String path = "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";


    //视频地址
    private String path = "http://baobab.wdjcdn.com/145076769089714.mp4";
    private Uri uri;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView;
    private CustomMediaController mCustomMediaController;
    private VideoView mVideoView;
    private boolean isLoad = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(getActivity());
//        ((PlayVideoOnLineActivity) getActivity()).getInstance().setLisenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_vedio, container, false);
        weakReference = new WeakReference<>(view);
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(this);
        return weakReference.get();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mCustomMediaController.setPadding(0, 0, 0, 0);
        } else {
            mCustomMediaController.setPadding(0, 0, 0, WindowUtils.getWindowHeith(getActivity()) / 3 * 2);
        }
        //屏幕切换时，设置全屏
        if (mVideoView != null) {
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
            mVideoView.start();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);

                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                mVideoView.start();
                pb.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("112233", "onPause()");
        mVideoView.pause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCustomMediaController = null;
        mVideoView = null;
        System.gc();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setPlaybackSpeed(1.0f);
    }

    @Override
    public void onGlobalLayout() {
        if (!isLoad) {
            isLoad = true;
            mVideoView = (VideoView) view.findViewById(R.id.buffer);
            mCustomMediaController = new CustomMediaController(getActivity(), mVideoView, getActivity());
            mCustomMediaController.setVideoName("白火锅 x 红火锅");
            pb = (ProgressBar) view.findViewById(R.id.probar);
            downloadRateView = (TextView) view.findViewById(R.id.download_rate);
            loadRateView = (TextView) view.findViewById(R.id.load_rate);
            uri = Uri.parse(path);
            mVideoView.setVideoURI(uri);//设置视频播放地址
            mCustomMediaController.show(5000);
            mVideoView.setMediaController(mCustomMediaController);
            mCustomMediaController.setAnchorView(mVideoView);
            mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//高画质
            mVideoView.requestFocus();
            mVideoView.setOnInfoListener(this);
            mVideoView.setOnBufferingUpdateListener(this);
            mVideoView.setOnPreparedListener(this);
//            mCustomMediaController.setPadding(0, 0, 0, WindowUtils.getWindowHeith(getActivity()) / 3 * 2);
        }
    }
}
