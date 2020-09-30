package Custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.egorgoshasigninup.R;
import com.example.egorgoshasigninup.User;

import java.util.List;

import Custom.DbBitmapUtility;

public class CustomAdapter extends BaseAdapter {
    List<User> users;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.item_lst, null);
        TextView email = view.findViewById(R.id.tvItem);
        ImageView imgv =  view.findViewById(R.id.imgv);

        email.setText(users.get(i).getEmail());
        imgv.setImageBitmap(DbBitmapUtility.getImage(users.get(i).getThumbnail()) );

        return view;
    }
}
