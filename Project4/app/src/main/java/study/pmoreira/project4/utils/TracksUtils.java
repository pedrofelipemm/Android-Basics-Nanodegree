package study.pmoreira.project4.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import study.pmoreira.project4.R;
import study.pmoreira.project4.model.Artist;
import study.pmoreira.project4.model.Track;

public abstract class TracksUtils {

    public static List<Track> fetchTracks(Context context) {
        List<Track> trackList = new ArrayList<>();
        Artist avantasia = new Artist(context.getString(R.string.artist1), context.getString(R.string.arttist1_description));
        Artist epica = new Artist(context.getString(R.string.artist2), context.getString(R.string.artist2_description));
        Artist nightwish = new Artist(context.getString(R.string.artist3), context.getString(R.string.artist3_description));

        trackList.add(newTrack("Shudder Before the Beautiful", 210, nightwish,
                R.drawable.nightwish_endless_forms_most_beatiful,
                R.drawable.nightwish_endless_forms_most_beatiful_thumb));

        trackList.add(newTrack("Weak Fantasy", 389, nightwish,
                R.drawable.nightwish_endless_forms_most_beatiful,
                R.drawable.nightwish_endless_forms_most_beatiful_thumb));

        trackList.add(newTrack("Élan", 285, nightwish,
                R.drawable.nightwish_endless_forms_most_beatiful,
                R.drawable.nightwish_endless_forms_most_beatiful_thumb));

        trackList.add(newTrack("Eidola", 159, epica,
                R.drawable.epica_holographic_principle,
                R.drawable.epica_holographic_principle_thumb));

        trackList.add(newTrack("Edge of the Blade", 274, epica,
                R.drawable.epica_holographic_principle,
                R.drawable.epica_holographic_principle_thumb));

        trackList.add(newTrack("A Phantasmic Parade", 276, epica,
                R.drawable.epica_holographic_principle,
                R.drawable.epica_holographic_principle_thumb));

        trackList.add(newTrack("Twisted Mind", 374, avantasia,
                R.drawable.avantasia_the_scarecrow,
                R.drawable.avantasia_the_scarecrow_thumb));

        trackList.add(newTrack("The Scarecrow", 368, avantasia,
                R.drawable.avantasia_the_scarecrow,
                R.drawable.avantasia_the_scarecrow_thumb));

        trackList.add(newTrack("Shelter From the Rain", 672, avantasia,
                R.drawable.avantasia_the_scarecrow,
                R.drawable.avantasia_the_scarecrow_thumb));

        return trackList;
    }

    private static Track newTrack(String name, int length, Artist artist, int coverId, int coverThumbId) {
        return new Track(name, length, artist, coverId, coverThumbId);
    }
}
