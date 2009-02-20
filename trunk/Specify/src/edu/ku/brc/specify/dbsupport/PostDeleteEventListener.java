/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package edu.ku.brc.specify.dbsupport;

import org.hibernate.event.PostDeleteEvent;

import edu.ku.brc.af.ui.forms.FormDataObjIFace;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;

/**
 * This class listens for Insert events from Hibernate so it can update the Lucene index. 
 * Note: that the Update is actually deleting the entry and this at the moment doesn't really do anything because
 * the record is already gone.
 * 
 * @author rods
 *
 * @code_status Complete
 *
 * Created Date: Nov 2, 2006
 *
 */
public class PostDeleteEventListener implements org.hibernate.event.PostDeleteEventListener
{

    /* (non-Javadoc)
     * @see org.hibernate.event.PostDeleteEventListener#onPostDelete(org.hibernate.event.PostDeleteEvent)
     */
    @Override
    public void onPostDelete(PostDeleteEvent obj)
    {
        if (obj.getEntity() instanceof FormDataObjIFace)
        {
            if (((FormDataObjIFace)obj.getEntity()).isChangeNotifier())
            {
                CommandDispatcher.dispatch(new CommandAction("Database", "Delete", obj.getEntity()));
            }
        } else
        {
            CommandDispatcher.dispatch(new CommandAction("Database", "Delete", obj.getEntity()));
        }
    }

}
