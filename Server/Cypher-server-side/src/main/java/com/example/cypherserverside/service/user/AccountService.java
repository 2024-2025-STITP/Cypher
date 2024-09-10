package com.example.cypherserverside.service.user;

public interface AccountService {
    /**
     *登录
     * @param email 用户邮箱
     * @param password  密码
     * @return 登陆的token
     */
    public String login(String email, String password);

    /**
     * @param username 用户名
     * @param email 邮箱
     * @param password 密码
     * @return 注册的token
     */
    public String register(String username, String email, String password);

    /**
     * @param token  当前登录状态的token
     * @param password 密码
     * @return 注销后的token
     */
    public String unregister(String token, String password);

    /**
     * @return 返回token
     */
    public String quit();

}
