package cn.unis.transit;

import java.io.Serializable;
import java.util.Date;

public class BaseReturnValue<T> implements Serializable{
	public static final long serialVersionUID = 456L;
	public int err_num;
	public String err_msg;
	public String req_id;
	public Date process_datetime;
	public T msg ;
	public int getErr_num() {
		return err_num;
	}
	public void setErr_num(int err_num) {
		this.err_num = err_num;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}
	public Date getProcess_datetime() {
		return process_datetime;
	}
	public void setProcess_datetime(Date process_datetime) {
		this.process_datetime = process_datetime;
	}
	public T getMsg() {
		return msg;
	}
	public void setMsg(T msg) {
		this.msg = msg;
	}


}
