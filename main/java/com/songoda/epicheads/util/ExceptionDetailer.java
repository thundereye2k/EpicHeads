package com.songoda.epicheads.util;

public abstract class ExceptionDetailer {

	private static class DetailException extends Exception {

		private static final long serialVersionUID = 7714839411923164464L;

		public DetailException(String detail) {
			super(detail);
		}
	}

	public RuntimeException detail(RuntimeException exception) {
		return (RuntimeException) detail((Exception) exception);
	}

	public abstract Exception detail(Exception exception);

	public static ExceptionDetailer constructorDetailer() {
		final DetailException constructorStackTrace = new DetailException("Object constructed at");

		return new ExceptionDetailer() {
			@Override
			public Exception detail(Exception exception) {
				try {
					return appendInfo(exception, constructorStackTrace);
				} catch (Exception e) {
					new Exception("Exception appending info to exception ", e).printStackTrace();

					constructorStackTrace.printStackTrace();

					return exception;
				}
			}
		};
	}

	public static Exception appendInfo(Exception exception, DetailException info) {
		Checks.ensureNonNull(exception, "exception");
		Checks.ensureNonNull(info, "info");

		exception.addSuppressed(info);

		return exception;
	}

}
