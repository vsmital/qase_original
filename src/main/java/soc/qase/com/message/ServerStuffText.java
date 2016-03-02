//---------------------------------------------------------------------
// Name:			ServerStuffText.java
// Author:			Bernard.Gorman@computing.dcu.ie
// Author:			Martin.Fredriksson@bth.se
//---------------------------------------------------------------------

package soc.qase.com.message;

import com.google.common.io.BaseEncoding;
import soc.qase.tools.Utils;

/*-------------------------------------------------------------------*/
/**	Message wrapper used when signalling stuff text information from
 *	host to client. */
/*-------------------------------------------------------------------*/
public class ServerStuffText extends Message
{
	private String stuffString = null;

/*-------------------------------------------------------------------*/
/**	Constructor. Parses the data and extracts message details.
 *	@param data message source */
/*-------------------------------------------------------------------*/
	public ServerStuffText(byte[] data, int off)
	{
		int stringLength = Utils.stringLength(data, off);
		stuffString = new String(Utils.stringValue(data, off, stringLength - 1));
		setLength(stringLength + 1);
		String characterInterpretation = "0d" + BaseEncoding.base16().encode(Utils.extractBytes(data, off, getLength())).toLowerCase();
		characterInterpretation = characterInterpretation.replaceAll("..", "$0 ");
		System.out.println(characterInterpretation);
	}

/*-------------------------------------------------------------------*/
/**	Get stuff string.
 *	@return stuff string. */
/*-------------------------------------------------------------------*/
	public String getStuffString()
	{
		return stuffString;
	}

/*-------------------------------------------------------------------*/
/**	Get String representation of this object.
 *	@return String representation of this object. */
/*-------------------------------------------------------------------*/
	public String toString()
	{
		return stuffString;
	}
}

