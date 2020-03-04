package univaq.aq.it.abruzzotourism.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDetails implements Parcelable {
    private String email;
    private String password;

    public  UserDetails(){}

    public UserDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected UserDetails(Parcel in) {
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
    }
}
