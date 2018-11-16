/*
 * ============LICENSE_START=======================================================
 * RAN Simulator - HoneyComb
 * ================================================================================
 * Copyright (C) 2018 Wipro Limited.
 * ================================================================================
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onap.ransim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.onap.ransim.websocket.model.Neighbor;
import org.onap.ransim.websocket.model.Topology;
import org.onap.ransim.websocket.model.UpdateCell;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.EditOperationType;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.NetconfConfigChange;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.NetconfConfigChangeBuilder;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.RadioAccess;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.netconf.config.change.Edit;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.netconf.config.change.EditBuilder;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.radio.access.FapService;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.radio.access.FapServiceKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.binding.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fd.honeycomb.notification.ManagedNotificationProducer;
import io.fd.honeycomb.notification.NotificationCollector;

/**
 * Notification producer for sample plugin
 */
public class DataChangeNotifnSender implements ManagedNotificationProducer {

    private static final Logger LOG = LoggerFactory.getLogger(DataChangeNotifnSender.class);
    private static final InstanceIdentifier<RadioAccess> ROOT_CONTAINER_ID = InstanceIdentifier.create(RadioAccess.class);

    private Thread thread;
    static NotificationCollector collector = null;

    public static void sendNotification(UpdateCell updCell) {
        LOG.info("RANSIM DataChangeNotifnSender sendNotification called ****** "+updCell.toString());
        List<Edit> editList = new ArrayList<Edit>();
        for(int i=0; i<updCell.getOneCell().getNeighborList().size(); i++) {
            Neighbor nbr = updCell.getOneCell().getNeighborList().get(i);
            EditBuilder edit = new EditBuilder();
            edit.setOperation(EditOperationType.Replace);
            @SuppressWarnings("deprecation")
			InstanceIdentifier<FapService> identifier = InstanceIdentifier.builder(RadioAccess.class).child(FapService.class,new FapServiceKey(nbr.getNodeId())).build();
            edit.setTarget(identifier);
            editList.add(edit.build());
        }
		final NetconfConfigChange notification = new NetconfConfigChangeBuilder()
        		.setDatastore(null)
        		.setEdit(editList)
                .build();
        LOG.info("Emitting notification: {}", notification);
        collector.onNotification(notification);
    }

    @Override
    public void start(@Nonnull final NotificationCollector collector) {
    	
        DataChangeNotifnSender.collector = collector;
        LOG.info("Starting notification stream for interfaces");

        // Simulating notification producer
        thread = new Thread(() -> {
            while(true) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                try {
                    List<Neighbor> nbrs = new ArrayList<Neighbor> ();
                    nbrs.add(new Neighbor("jio", "1", 1, "ncserver1001", "ncserver1001"));
                    UpdateCell updCell = new UpdateCell("1", "127.0.0.1", "50000"
                            , new Topology("ncserver1001", 1, "51", nbrs));
                    Thread.sleep(60000);
                    sendNotification(updCell);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "NotificationProducer");
        thread.setDaemon(true);
        thread.start();        
    }

    @Override
    public void stop() {
        if(thread != null) {
            thread.interrupt();
        }
    }

    @Nonnull
    @Override
    public Collection<Class<? extends Notification>> getNotificationTypes() {
        // Producing only this single type of notification
        return Collections.singleton(NetconfConfigChange.class);
    }

    @Override
    public void close() throws Exception {
        stop();
    }
}

