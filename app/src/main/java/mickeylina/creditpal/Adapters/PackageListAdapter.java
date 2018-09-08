package mickeylina.creditpal.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mickeylina.creditpal.R;

public class PackageListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] amount;
    private final String[] cost;

    public PackageListAdapter(Activity context, String[] amount, String[] cost) {
        super (context, R.layout.item_package,amount);
        this. context = context;
        this.amount = amount;
        this.cost = cost;
    }

    static class ViewHolder {
        TextView amount;
        TextView cost;
        TextView amountAvatar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_package, parent, false);
            viewHolder.amount = convertView.findViewById(R.id.textAmount);
            viewHolder.amountAvatar = convertView.findViewById(R.id.avatar);
            viewHolder.cost = convertView.findViewById(R.id.textCost);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.amount.setText(amount[position]);
        viewHolder.amountAvatar.setText(amount[position]);
        viewHolder.cost.setText(cost[position]);
        return convertView;
    }

}

