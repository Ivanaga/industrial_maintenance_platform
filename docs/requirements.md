Industrial Maintenance Platform — Requirements

1. Overview

The Industrial Maintenance Platform is a software system for managing industrial equipment, maintenance activities, and equipment-related incidents.

The long-term goal of the platform is to collect machine telemetry, monitor equipment condition, detect abnormal behavior, and use historical data and machine learning to predict potential equipment failures.

The first version focuses on the core asset and maintenance management functionality.

2. Actors

Administrator

Responsible for managing machines and system users.

Engineer

Monitors equipment and incidents and manages maintenance activities.

Technician

Performs maintenance tasks assigned to them.

3. Core Domain

The system contains the following main concepts:

Plant
Machine
User
Maintenance Task
Incident

Future versions will introduce:

Measurement
Prediction
Sensor
Notification

4. Functional Requirements — V0.1
FR-01 — Machine Management

The system shall allow authorized users to:

register a machine;
view a machine;
view all registered machines;
update machine information;
deactivate a machine.

Each machine shall contain at least:

unique identifier;
name;
serial number;
machine type;
manufacturer;
installation date;
location;
operational status.

FR-02 — Maintenance Task Management

The system shall allow engineers to create maintenance tasks for machines.

A maintenance task shall contain:

associated machine;
description;
priority;
status;
creation date;
due date;
assigned technician.

The system shall support the maintenance workflow:

OPEN → IN_PROGRESS → COMPLETED

FR-03 — Maintenance History

The system shall preserve completed maintenance tasks so that the maintenance history of a machine can be inspected.

FR-04 — Incident Management

The system shall allow incidents to be associated with machines.

An incident shall contain:

associated machine;
description;
severity;
status;
creation timestamp.
FR-05 — User Roles

The system shall distinguish between:

Administrator;
Engineer;
Technician.

Operations shall be restricted according to the user's role.

5. Future Requirements

Future versions of the platform may support:

continuous machine telemetry ingestion;
sensor simulation;
Apache Kafka event streaming;
automatic incident detection;
predictive maintenance using machine learning;
real-time dashboards;
notifications;
industrial protocols such as MQTT or OPC UA;
monitoring and observability;
cloud deployment.