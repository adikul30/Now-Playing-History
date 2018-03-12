package kulkarni.aditya.nowplayinghistory;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by maverick on 3/11/18.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private List<String> songList;
    private Context mContext;

    public SongAdapter(List<String> songList, Context mContext) {
        this.songList = songList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(songList.get(position));
    }

    @Override
    public int getItemCount() {
        if (songList == null) return 0;
        else return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        LinearLayout songLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.song_title);
            songLayout = itemView.findViewById(R.id.song_layout);
            songLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String songTitle = songList.get(getAdapterPosition());
                    ClipboardManager clipboardManager = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Song name", songTitle);
                    clipboardManager.setPrimaryClip(clip);
                    Toast.makeText(mContext,"Copied to clipboard.",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
