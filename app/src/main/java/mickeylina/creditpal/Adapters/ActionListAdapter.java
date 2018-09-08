package mickeylina.creditpal.Adapters;

import android.app.Activity;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mickeylina.creditpal.R;

public class ActionListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] action;
    private  final TypedArray typedArray;

    public ActionListAdapter(Activity context, String[] action, TypedArray iconID) {
        super (context, R.layout.item_actions,action );
        this. context = context;
        this.action = action;
        this. typedArray = iconID;
    }

    static class ViewHolder {
        TextView actionName;
        ImageView iconID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_actions, parent, false);
            viewHolder.actionName = convertView.findViewById(R.id.action_info);
            viewHolder.iconID =  convertView.findViewById(R.id.icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.actionName.setText(action[position]);
        viewHolder.iconID.setImageResource(typedArray.getResourceId(position,position));
        return convertView;
    }

}

