//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.result;

import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
public class JsonResult<T> implements Serializable {
    private Integer state;
    private String message;
    private T data;
    private String resultUsername;
    private String token;

    public JsonResult() {
    }

    public Integer getState() {
        return this.state;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public String getResultUsername() {
        return this.resultUsername;
    }

    public String getToken() {
        return this.token;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setResultUsername(String resultUsername) {
        this.resultUsername = resultUsername;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof JsonResult)) {
            return false;
        } else {
            JsonResult<?> other = (JsonResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71: {
                    Object this$state = this.getState();
                    Object other$state = other.getState();
                    if (this$state == null) {
                        if (other$state == null) {
                            break label71;
                        }
                    } else if (this$state.equals(other$state)) {
                        break label71;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                label57: {
                    Object this$data = this.getData();
                    Object other$data = other.getData();
                    if (this$data == null) {
                        if (other$data == null) {
                            break label57;
                        }
                    } else if (this$data.equals(other$data)) {
                        break label57;
                    }

                    return false;
                }

                Object this$resultUsername = this.getResultUsername();
                Object other$resultUsername = other.getResultUsername();
                if (this$resultUsername == null) {
                    if (other$resultUsername != null) {
                        return false;
                    }
                } else if (!this$resultUsername.equals(other$resultUsername)) {
                    return false;
                }

                Object this$token = this.getToken();
                Object other$token = other.getToken();
                if (this$token == null) {
                    if (other$token == null) {
                        return true;
                    }
                } else if (this$token.equals(other$token)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof JsonResult;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $state = this.getState();
        result = result * 59 + ($state == null ? 43 : $state.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $resultUsername = this.getResultUsername();
        result = result * 59 + ($resultUsername == null ? 43 : $resultUsername.hashCode());
        Object $token = this.getToken();
        result = result * 59 + ($token == null ? 43 : $token.hashCode());
        return result;
    }

    public String toString() {
        return "JsonResult(state=" + this.getState() + ", message=" + this.getMessage() + ", data=" + this.getData() + ", resultUsername=" + this.getResultUsername() + ", token=" + this.getToken() + ")";
    }
}
