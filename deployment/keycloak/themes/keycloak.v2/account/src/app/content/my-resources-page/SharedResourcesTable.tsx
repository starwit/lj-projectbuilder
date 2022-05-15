/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import * as React from 'react';

import {
    Chip,
    ChipGroup,
    DataList,
    DataListCell,
    DataListItem,
    DataListItemCells,
    DataListItemRow
} from '@patternfly/react-core';
import {RepositoryIcon} from '@patternfly/react-icons';


import {PaginatedResources, Resource, Scope} from "./resource-model";
import {Msg} from '../../widgets/Msg';
import {AbstractResourcesTable, ResourcesTableState} from './AbstractResourceTable';
import EmptyMessageState from '../../widgets/EmptyMessageState';

export interface ResourcesTableProps {
    resources: PaginatedResources;
    noResourcesMessage: string;
}

export class SharedResourcesTable extends AbstractResourcesTable<ResourcesTableState> {

    public constructor(props: ResourcesTableProps) {
        super(props);
        this.state = {
            permissions: new Map()
        }
    }

    public render(): React.ReactNode {
        if (this.props.resources.data.length === 0) {
            return (
                    <EmptyMessageState icon={RepositoryIcon} messageKey="noResourcesSharedWithYou"/>
            );
        }
        return (
                <DataList aria-label={Msg.localize('resources')} id="sharedResourcesList">
                    <DataListItem key='resource-header' aria-labelledby='resource-header'>
                        <DataListItemRow>
                            <DataListItemCells
                                    dataListCells={[
                                        <DataListCell key='resource-name-header' width={2}>
                                            <strong><Msg msgKey='resourceName'/></strong>
                                        </DataListCell>,
                                        <DataListCell key='application-name-header' width={2}>
                                            <strong><Msg msgKey='application'/></strong>
                                        </DataListCell>,
                                        <DataListCell key='permission-header' width={2}/>,
                                        <DataListCell key='requests-header' width={2}/>,
                                    ]}
                            />
                        </DataListItemRow>
                    </DataListItem>
                    {this.props.resources.data.map((resource: Resource, row: number) => (
                            <DataListItem key={'resource-' + row} aria-labelledby={resource.name}>
                                <DataListItemRow>
                                    <DataListItemCells
                                            dataListCells={[
                                                <DataListCell key={'resourceName-' + row} width={2}>
                                                    <Msg msgKey={resource.name}/>
                                                </DataListCell>,
                                                <DataListCell key={'resourceClient-' + row} width={2}>
                                                    <a href={resource.client.baseUrl}>{this.getClientName(resource.client)}</a>
                                                </DataListCell>,
                                                <DataListCell key={'permissions-' + row} width={2}>
                                                    {resource.scopes.length > 0 &&
                                                            <ChipGroup categoryName={Msg.localize('permissions')}>
                                                                {
                                                                    resource.scopes.map(scope => (
                                                                            <Chip key={scope.name} isReadOnly>
                                                                                {scope.displayName || scope.name}
                                                                            </Chip>
                                                                    ))
                                                                }
                                                            </ChipGroup>}
                                                </DataListCell>,
                                                <DataListCell key={'pending-' + row} width={2}>
                                                    {resource.shareRequests.length > 0 &&
                                                            <ChipGroup categoryName={Msg.localize('pending')}>
                                                                {
                                                                    (resource.shareRequests[0].scopes as Scope[]).map(scope => (
                                                                            <Chip key={scope.name} isReadOnly>
                                                                                {scope.displayName || scope.name}
                                                                            </Chip>
                                                                    ))
                                                                }
                                                            </ChipGroup>
                                                    }
                                                </DataListCell>
                                            ]}
                                    />
                                </DataListItemRow>
                            </DataListItem>
                    ))}
                </DataList>
        );
    }
}
