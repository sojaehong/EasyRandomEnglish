package tour.app.english.com.tourapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tour.app.english.com.tourapp.R;
import tour.app.english.com.tourapp.VO.QuestionVO;

public class FragmentListAdapter extends BaseAdapter{
    private int layout;
    private ArrayList<QuestionVO> list;
    private LayoutInflater inflater;

    public FragmentListAdapter(Context context, int layout, ArrayList<QuestionVO> list){
        this.layout = layout;
        this.list = list;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(layout,null);
        }

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(position + 1);
        stringBuffer.append(". ");
        stringBuffer.append(list.get(position).getKoreanQuestion());

        TextView textView = convertView.findViewById(R.id.text_korean_question);
        textView.setText(stringBuffer.toString());

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
