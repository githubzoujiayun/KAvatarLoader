package com.kohoh.kavatarloader;

/**
* Created by kohoh on 14-8-24.
*/
public class Constant {
    public static final String DOSENT_EXIST_EMAIL = "doesntexist@example.com";
    public static final String EXIST_EMAIL1 = "kavatarloader1@126.com";
    public static final String EXIST_EMAIL2 = "kavatarloader2@126.com";

    public static final String EXIST_EMAIL1_HASH_CODE = "79494f79a67ea995a8f128b8331b3306";
    public static final String EXIST_EMAIL2_HASH_CODE = "228ff1d1d1910536d99790691eb45882";
    public static final String DOSENT_EXIST_EMAIL_HASH_CODE = "628df4c8f4d7c3bed231df493987e808";

    public static final String EXIST_EMAIL1_D_404_URL = "http://www.gravatar.com/avatar/79494f79a67ea995a8f128b8331b3306.jpg?d=404";
    public static final String EXIST_EMAIL2_D_404_URL = "http://www.gravatar.com/avatar/228ff1d1d1910536d99790691eb45882.jpg?d=404";
    public static final String DOSENT_EXIST_EMAIL_D_404_RUL = "http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=404";

    public static final String DOSENT_EXIST_EMAIL_D_MONSTERID_RUL = "http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=monsterid";
    public static final String DOSENT_EXIST_EMAIL_D_IDENTICON_RUL = "http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=identicon";
    public static final String DOSENT_EXIST_EMAIL_D_WAVATAR_RUL = "http://www.gravatar.com/avatar/628df4c8f4d7c3bed231df493987e808.jpg?d=wavatar";

    public static TaskParmUseEmail getTaskParmUseEmail(String email) {
        TaskParmUseEmail parm = new TaskParmUseEmail();
        parm.setEmail(email);
        return parm;
    }
}
