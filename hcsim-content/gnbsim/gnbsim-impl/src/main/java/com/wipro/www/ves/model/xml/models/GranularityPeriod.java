/*
 * Copyright (C) 2018 Wipro Limited.
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

package com.wipro.www.ves.model.xml.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GranularityPeriod {
    private String endTime;
    private String duration;
   
    public GranularityPeriod() {

}
   
    public GranularityPeriod(String endTime, String duration) {
super();
this.endTime = endTime;
this.duration = duration;
}
@XmlAttribute
public String getEndTime() {
return endTime;
}
public void setEndTime(String endTime) {
this.endTime = endTime;
}
@XmlAttribute
public String getDuration() {
return duration;
}
public void setDuration(String duration) {
this.duration = duration;
}
@Override
public String toString() {
return "GranularityPeriod [endTime=" + endTime + ", duration=" + duration + "]";
}
}


