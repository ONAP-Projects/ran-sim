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

package org.onap.ransim.write;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.onap.ransim.CrudService;
import io.fd.honeycomb.translate.spi.write.ListWriterCustomizer;
import io.fd.honeycomb.translate.spi.write.WriterCustomizer;
import io.fd.honeycomb.translate.write.WriteContext;
import io.fd.honeycomb.translate.write.WriteFailedException;
import javax.annotation.Nonnull;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.radio.access.fap.service.cell.config.lte.lte.ran.LteRanNeighborListInUse;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.radio.access.fap.service.cell.config.lte.lte.ran.LteRanNeighborListInUseBuilder;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.radio.access.fap.service.cell.config.lte.lte.ran.lte.ran.neighbor.list.in.use.LteRanNeighborListInUseLteCell;
import org.opendaylight.yang.gen.v1.urn.onf.otcc.wireless.yang.radio.access.rev180920.radio.access.fap.service.cell.config.lte.lte.ran.lte.ran.neighbor.list.in.use.LteRanNeighborListInUseLteCellBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

/**
 * Writer for {@link LteRanNeighborListInUseLteCell} list node from our YANG model.
 */
public final class LteRanNeighborListInUseLteCellCustomizer implements WriterCustomizer<LteRanNeighborListInUseLteCell> {

    private final CrudService<LteRanNeighborListInUseLteCell> crudService;
    private static final Logger LOG = LoggerFactory.getLogger(LteRanNeighborListInUseLteCellCustomizer.class);

    public LteRanNeighborListInUseLteCellCustomizer(@Nonnull final CrudService<LteRanNeighborListInUseLteCell> crudService) {
        LOG.info("RANSIM LteRanNeighborListInUseLteCellCustomizer ctor called");
        this.crudService = crudService;
    }

    @Override
    public void writeCurrentAttributes(@Nonnull final InstanceIdentifier<LteRanNeighborListInUseLteCell> id, @Nonnull final LteRanNeighborListInUseLteCell dataAfter,
                                       @Nonnull final WriteContext writeContext) throws WriteFailedException {
        LOG.info("RANSIM LteRanNeighborListInUseLteCellCustomizer writeCurrentAttributes called");
        //perform write of data,or throw exception
        //invoked by PUT operation,if provided data doesn't exist in Config data
        crudService.writeData(id, dataAfter, writeContext);
    }

    @Override
    public void updateCurrentAttributes(@Nonnull final InstanceIdentifier<LteRanNeighborListInUseLteCell> id,
                                        @Nonnull final LteRanNeighborListInUseLteCell dataBefore,
                                        @Nonnull final LteRanNeighborListInUseLteCell dataAfter, @Nonnull final WriteContext writeContext)
            throws WriteFailedException {
        LOG.info("RANSIM LteRanNeighborListInUseLteCellCustomizer updateCurrentAttributes called");
        //perform update of data,or throw exception
        //invoked by PUT operation,if provided data does exist in Config data
        crudService.updateData(id, dataBefore, dataAfter);
    }

    @Override
    public void deleteCurrentAttributes(@Nonnull final InstanceIdentifier<LteRanNeighborListInUseLteCell> id,
                                        @Nonnull final LteRanNeighborListInUseLteCell dataBefore,
                                        @Nonnull final WriteContext writeContext) throws WriteFailedException {
        LOG.info("RANSIM LteRanNeighborListInUseLteCellCustomizer deleteCurrentAttributes called");
        //perform delete of data,or throw exception
        //invoked by DELETE operation
        crudService.deleteData(id, dataBefore);
    }
}
