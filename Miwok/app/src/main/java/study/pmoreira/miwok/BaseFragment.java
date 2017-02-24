package study.pmoreira.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import static android.content.Context.AUDIO_SERVICE;

public abstract class BaseFragment extends Fragment {

    private List<Word> words;

    protected MediaPlayer mediaPlayer;

    protected AudioManager audioManager;

    protected AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Word word = words.get(position);

            releaseMediaPlayer();
            mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResorceId());
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(onCompletionListener);
        }
    };

    protected MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager
                            .AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        words = initWords();

        return view;
    }

    protected void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    protected abstract List<Word> initWords();

    public List<Word> getWords() {
        return words;
    }
}
