package joao.vitor.oliveira.galeriapublica;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;


public class GridAdapter extends PagingDataAdapter<ImageData,MyViewHolder> {
    public GridAdapter(DiffUtil.ItemCallback<ImageData> diffCallback) {
        super(diffCallback);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageData imageData = getItem(position);

        Bitmap thumb = imageData.thumb;
        ImageView imageView = holder.itemView.findViewById(R.id.imThumb);
        imageView.setImageBitmap(thumb);
    }
}

