package tour.app.english.com.tourapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tour.app.english.com.tourapp.Adapter.FragmentListAdapter;
import tour.app.english.com.tourapp.GetJsonData;
import tour.app.english.com.tourapp.QuestionActivity;
import tour.app.english.com.tourapp.R;
import tour.app.english.com.tourapp.VO.QuestionVO;

public class FragmentAirport extends Fragment {
    private ListView listView;
    private FragmentListAdapter fragmentListAdapter;
    private ArrayList<QuestionVO> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airport, null);

        //json 파일 파싱해서 리스트에 담아줌
        try {
            list = getJsonItem();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //listview에 리스트 어댑터 셋팅
        listView = view.findViewById(R.id.list_view_airport);
        fragmentListAdapter = new FragmentListAdapter(getContext(),R.layout.fragment_list_item,list);
        listView.setAdapter(fragmentListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                intent.putExtra("englishQuestion", list.get(position).getEnglishQuestion());
                intent.putExtra("answer", list.get(position).getAnswer());
                startActivity(intent);
            }
        });

        return view;
    }

    //리스트 아이템 클릭 리스너
    private ArrayList<QuestionVO> getJsonItem() throws JSONException {
        ArrayList<QuestionVO> list = new ArrayList<>();
        JSONObject jsonObject = new GetJsonData().getJsonObject(getContext(), "airport.json");
        JSONArray jsonArray = new JSONArray(jsonObject.getString("airport"));

        for(int i = 0; i < jsonArray.length(); i++){
            QuestionVO questionVO = new QuestionVO();
            questionVO.setKoreanQuestion(jsonArray.getJSONObject(i).getString("koreanQuestion"));
            questionVO.setEnglishQuestion(jsonArray.getJSONObject(i).getString("englishQuestion"));
            questionVO.setAnswer(jsonArray.getJSONObject(i).getString("answer"));
            list.add(questionVO);
        }

        return list;
    }
}
