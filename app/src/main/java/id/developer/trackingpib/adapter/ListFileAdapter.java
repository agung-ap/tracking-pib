package id.developer.trackingpib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.developer.trackingpib.R;
import id.developer.trackingpib.model.ListFileModel;
import id.developer.trackingpib.model.Message;

public class ListFileAdapter extends RecyclerView.Adapter<ListFileAdapter.ViewHolder> {
    private Context context;
    private List<ListFileModel> listFile;

    public ListFileAdapter(Context context) {
        this.context = context;
        listFile = new ArrayList<>();
    }

    public void setData(List<ListFileModel> listFile) {
        this.listFile = listFile;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_file, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.filename.setText(listFile.get(i).getFilename());
        viewHolder.filepath.setText(listFile.get(i).getFilepath());
    }

    @Override
    public int getItemCount() {
        return listFile == null ? 0 : listFile.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView filename, filepath;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            filename = itemView.findViewById(R.id.filename);
            filepath = itemView.findViewById(R.id.filepath);
        }
    }
}
