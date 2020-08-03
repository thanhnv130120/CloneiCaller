package com.example.cloneicaller;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity  extends AppCompatActivity {
    CallStateReceiver callStateReceiver;
//    private FragmentCallKeyboard fragmentCallKeyboard = new FragmentCallKeyboard();
//    private FragmentListBlock fragmentListBlock = new FragmentListBlock();
//    private FragmentListHistory fragmentListHistory = new FragmentListHistory();
//    private FragmentSetting fragmentSetting = new FragmentSetting();
//    private ArrayList<ItemPerson> persons = new ArrayList<>();
//    private DataReceiverListener listener;
//    private Bundle bundle = new Bundle();
//    public ArrayList<ItemPerson> getPersons() {
//        return persons;
//    }
//
//    public void setPersons(ArrayList<ItemPerson> persons) {
//        this.persons = persons;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        initReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(callStateReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void init() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.container_view,
//                new FragmentHome()).commit();

    }
    private void initReceiver() {
        callStateReceiver = new CallStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        registerReceiver(callStateReceiver,filter);
    }
//    private BottomNavigationView.OnNavigationItemSelectedListener botListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//            Fragment selectedFragment = null;
//            switch (menuItem.getItemId()){
//                case R.id.menu_keyboard:
//                    selectedFragment = new FragmentCallKeyboard();
//                    break;
//                case R.id.menu_history:
//                    selectedFragment = new FragmentListHistory();
//                    break;
//                case R.id.menu_block:
//                    selectedFragment = new FragmentListBlock();
//                    break;
//                case R.id.menu_setting:
//                    selectedFragment = new FragmentSetting();
//                    break;
//            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.container_view,selectedFragment).commit();
//            return true;
//        }
//    };

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.img_pad:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container_view, fragmentCallKeyboard).commit();
//                break;
//            case R.id.img_history:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container_view, fragmentListHistory).commit();
//                break;
//            case R.id.img_block:
//                fragmentListBlock.setArguments(bundle);
//                getSupportFragmentManager().beginTransaction().replace(R.id.container_view, fragmentListBlock).commit();
//                break;
//            case R.id.img_setting:
//        }
//    }

//    @Override
//    public void onPutListDiarySent(ArrayList<ItemPerson> people) {
////        for (int i = 0; i < people.size(); i++) {
////            persons.add(people.get(i));
////        }
//        bundle.putSerializable("ModelList", people);
//        fragmentListBlock.setArguments(bundle);
//    }
//    public interface DataReceiverListener {
//        void onReceived(int requestCode, int resultCode, Intent data);
//    }
//
//    public void setListener(DataReceiverListener listener) {
//        this.listener = listener;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (listener != null) {
//            int request = 101;
//            int result = 2;
//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("ModelList",persons);
//            intent.putExtras(bundle);
//            listener.onReceived(request, result, intent);
//        }
//    }
}
