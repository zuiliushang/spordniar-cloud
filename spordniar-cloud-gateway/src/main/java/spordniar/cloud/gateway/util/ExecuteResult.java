package spordniar.cloud.gateway.util;

public class ExecuteResult<Rt, Et> {

	private boolean success;
	
	private Rt rightResult;
	
	private Et errorResult;

	public ExecuteResult() {
		super();
	}

	public ExecuteResult(boolean success, Rt rightResult, Et errorResult) {
		super();
		this.success = success;
		this.rightResult = rightResult;
		this.errorResult = errorResult;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Rt getRightResult() {
		return rightResult;
	}

	public void setRightResult(Rt rightResult) {
		this.rightResult = rightResult;
	}

	public Et getErrorResult() {
		return errorResult;
	}

	public void setErrorResult(Et errorResult) {
		this.errorResult = errorResult;
	}
	
}
