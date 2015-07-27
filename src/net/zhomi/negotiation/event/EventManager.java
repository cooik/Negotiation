package net.zhomi.negotiation.event;


public abstract class EventManager {
	
	public  abstract void 	addEvent(Event event);
	
	public  abstract void	removeEvent(int nEventCode);
	
	public  abstract void 	removeAllEvent();
	
	/** ִ��event
	 * 
	 * @param nEventCode	event���
	 * @param delayMillis
	 * @param params
	 */
	public 	abstract void 	postEvent(int nEventCode,long delayMillis,Object ... params);
	
	public	abstract void 	postEvent(final Event event,long delayMillis,Object ... params);
	
	public	abstract Event	runEvent(int nEventCode,Object ... params);
	
	public 	abstract Event	runEvent(final Event event,Object ... params);
	
	public	abstract void 	addEventListener(int nEventCode,OnEventListener listener,boolean bOnce);
	
	public  abstract void	removeEventListener(int nEventCode,OnEventListener listener);
	
	public static interface OnEventListener{
		public void onEventRunEnd(Event event);
	}
}
