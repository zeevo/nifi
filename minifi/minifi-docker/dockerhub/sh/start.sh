#!/bin/sh -e

#    Licensed to the Apache Software Foundation (ASF) under one or more
#    contributor license agreements.  See the NOTICE file distributed with
#    this work for additional information regarding copyright ownership.
#    The ASF licenses this file to You under the Apache License, Version 2.0
#    (the "License"); you may not use this file except in compliance with
#    the License.  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the License for the specific language governing permissions and
#    limitations under the License.

# Continuously provide logs so that 'docker logs' can produce them
tail -F "${MINIFI_HOME}/logs/minifi-app.log" &
"${MINIFI_HOME}/bin/minifi.sh" run &
minifi_pid="$!"

trap 'echo Received trapped signal, beginning shutdown...;./bin/minifi.sh stop;exit 0;' TERM HUP INT;
trap ":" EXIT

echo MiNiFi running with PID ${minifi_pid}.
wait ${minifi_pid}