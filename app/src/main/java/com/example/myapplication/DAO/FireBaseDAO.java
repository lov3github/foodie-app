package com.example.myapplication;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBaseDAO {

    public static final String KEY_USERS_REFERENCE_PATH = "users";

    public static final String KEY_PHONENUMBER_CHILD_PATH = "phoneNumber";
    public static final String KEY_PASSWORD_CHILD_PATH = "password";
    public static final String KEY_DATEOFBIRTH_CHILD_PATH = "dateOfBirth";
    public static final String KEY_FULLNAME_CHILD_PATH = "fullName";

    public static final String KEY_EMAIL_CHILD_PATH = "email";

    public static final String KEY_GENDER_MALE_STRING = "Male";
    public static final String KEY_GENDER_FEMALE_STRING = "Female";
    public static final String KEY_GENDER_HIDE_STRING = "Hide";

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(KEY_USERS_REFERENCE_PATH);
    Context context;

    // Interface

    public interface ArrayListUserCallback {
        /**
         * The work of this method: Chỉ định công việc khi dữ liệu của arrayList nạp xong
         *
         * @param arrayList chứa dữ liệu dưới dạng ArrayList<User>
         */
        void onArrayListReceived(ArrayList<User> arrayList);
    }

    public interface ArrayListStringCallback {
        /**
         * The work of this method: Chỉ định công việc khi dữ liệu của arrayList nạp xong
         *
         * @param arrayList chứa dữ liệu dưới dạng ArrayList<String>
         */
        void onArrayListReceived(ArrayList<String> arrayList);
    }

    public interface KeyCallback {
        /**
         * The work of this method: Chỉ định công việc khi dữ liệu của key nạp xong
         *
         * @param key là key dưới dạng String
         */
        void onKeyReceived(String key);
    }
    //Constructor

    /**
     * The work of this method: Khởi tạo đối tượng FirebaseDAO
     *
     * @param context là context của activity
     */
    public FireBaseDAO(Context context) {
        this.context = context;
    }


    //Method

    /**
     * The work of this method: Thêm đối 1 tượng User vào realtime firebase mục users.
     *
     * @param user là đối tượng chứa dữ liệu được thêm vào
     */
    public void addUser(User user) {
        String userKey = mDatabase.push().getKey();
        mDatabase.child(userKey).setValue(user);
    }

    /**
     * The work of this method: Cập nhật đối 1 tượng User vào realtime firebase mục users.
     *
     * @param userKey là key của đối tượng chứa dữ liệu cần sửa.
     * @param user    là đối tượng chứa dữ liệu thay thế
     */
    public void updateUser(String userKey, User user) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(user.getPhoneNumber()))
            mDatabase.child(userKey).child(KEY_PHONENUMBER_CHILD_PATH).setValue(user.getPhoneNumber());

        if (!TextUtils.isEmpty(user.getPassword()))
            mDatabase.child(userKey).child(KEY_PASSWORD_CHILD_PATH).setValue(user.getPassword());
    }

    /**
     * The work of this method: Tạm thời chưa dùng được
     *
     * @param userKey là key của đối tượng chứa dữ liệu cần sửa.
     * @param user    là đối tượng chứa dữ liệu thay thế
     */
    private void writeNewPost(String userKey, User user) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.push().getKey();
        Map<String, Object> postValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }


    /**
     * The work of this method: Tìm tới và loại bỏ user có key tương ứng ra khỏi database
     *
     * @param userKey là key của đối tượng chứa dữ liệu cần "bay màu".
     */
    public void removeUser(String userKey) {
        Query query = mDatabase.orderByKey().equalTo(userKey);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Iterate over the snapshot to delete each child
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    // Use the removeValue() method to delete the child
                    childSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }

    /**
     * The work of this method: lấy toàn bộ user trên database và gắn nó vào 1 ArrayList,
     * khi ArrayList này đã được nạp dữ liệu xong,
     * bạn có thể gọi nó thông qua tham số của hàm onArrayListReceived thuộc interface ArrayListUserCallback.
     *
     * @param arrayListCallback là 1 interface ArrayListStringCallback.
     */
    public void getAll(final ArrayListStringCallback arrayListCallback) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            //            addValueEventListener
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method will be called once with the entire contents of the database.

                // Iterate through all the child nodes of the database and add them to an ArrayList
                ArrayList<String> arrayList1 = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User value = snapshot.getValue(User.class);
                    arrayList1.add(value.toString());
                }

                // Use the ArrayList here as needed
                arrayListCallback.onArrayListReceived(arrayList1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
                arrayListCallback.onArrayListReceived(null);

            }
        });


    }

    /**
     * The work of this method: lấy toàn bộ user có điều kiện trên database và gắn nó vào 1 ArrayList,
     * khi ArrayList này đã được nạp dữ liệu xong,
     * bạn có thể gọi nó thông qua tham số của hàm onArrayListReceived thuộc interface ArrayListUserCallback.
     *
     * @param wherePath         là path con dạng string chỉ tên trường dữ liệu nào cần so sánh.
     * @param equalValue        là giá trị dữ liệu so sánh bằng với các dữ liệu lấy từ trường.
     * @param arrayListCallback là 1 interface ArrayListUserCallback.
     */
    public void getAll(String wherePath, String equalValue, final ArrayListUserCallback arrayListCallback) {
        ArrayList<User> arrayList = new ArrayList<>();

        Query query = mDatabase.orderByChild(wherePath).equalTo(equalValue);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method will be called once with the entire contents of the database.

                // Iterate through all the child nodes of the database and add them to an ArrayList
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User value = snapshot.getValue(User.class);
                    arrayList.add(value);
                }

                arrayListCallback.onArrayListReceived(arrayList);
                // Use the ArrayList here as needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that may occur
                arrayListCallback.onArrayListReceived(null);
            }
        });
    }

    /**
     * The work of this method: lấy Key của user có phoneNumber chỉ định trên database và gắn nó vào 1 String,
     * khi String này đã được nạp dữ liệu xong,
     * bạn có thể gọi nó thông qua tham số của hàm onKeyReceived thuộc interface KeyCallback.
     *
     * @param numberPhoneEqualValue         là giá trị dữ liệu so sánh bằng với các dữ liệu lấy từ trường phoneNumber.
     * @param callback là 1 interface KeyCallback.
     */
    public void getKey(String numberPhoneEqualValue, final KeyCallback callback) {
        Query query = mDatabase.orderByChild(KEY_PHONENUMBER_CHILD_PATH).equalTo(numberPhoneEqualValue);
        query.keepSynced(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = null;
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    key = childSnapshot.getKey();
                    break; // Only get the first key
                }
                callback.onKeyReceived(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                callback.onKeyReceived(null);
            }
        });
    }

    /**
     * The work of this method: bạn quá chán nản khi không có dữ liệu để chạy các hàm của class này,
     * bạn không có thời gian để nhập đúng các thông tin cho từng trường dữ liệu,
     * và bạn quá lười phải thêm dữ liệu user vào database,
     * không sao bạn chỉ cần gọi cái hàm này và nó sẽ thêm cho bạn 6 đối tượng để bạn có thông tin mà sài
     * -----------  HEHEBoy :)) -----------
     */
    public void createDemoDataSource() {
        ArrayList<User> arrayListDemoData = new ArrayList<>();
        arrayListDemoData.add(new User(
                        "Nguyễn Văn A",
                        "012345678",
                        "monleosa124",
                        "nguyenA@gmail.com",
                        "01/12/1994",
                        KEY_GENDER_MALE_STRING
                )
        );

        arrayListDemoData.add(new User(
                        "Nguyễn Văn B",
                        "0987654432",
                        "monleosa124",
                        "nguyenb@gmail.com",
                        "11/01/1999",
                        KEY_GENDER_MALE_STRING
                )
        );

        arrayListDemoData.add(new User(
                        "Trần Văn C",
                        "099988877",
                        "monleosa124",
                        "tranC@gmail.com",
                        "08/06/2001",
                        KEY_GENDER_MALE_STRING
                )
        );

        arrayListDemoData.add(new User(
                        "Nguyễn Trần E",
                        "0112233445",
                        "monleosa124",
                        "nguyenE@yahoo.com",
                        "24/02/1977",
                        KEY_GENDER_FEMALE_STRING
                )
        );

        arrayListDemoData.add(new User(
                        "Nguyễn Thị D",
                        "012345679",
                        "monleosa124",
                        "nguyend@gmail.com",
                        "24/03/1991",
                        KEY_GENDER_FEMALE_STRING
                )
        );

        arrayListDemoData.add(new User(
                        "Nguyễn Văn Tèo",
                        "0888888888",
                        "monleosa124",
                        "TeoLaAiA@gmail.com",
                        "08/08/1800",
                        KEY_GENDER_HIDE_STRING
                )
        );

        for (User arrItem : arrayListDemoData) {
            addUser(arrItem);
        }

    }

}
