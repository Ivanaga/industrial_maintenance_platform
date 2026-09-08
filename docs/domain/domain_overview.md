# Domain Overview

## 1. Purpose

The Industrial Maintenance Platform is a software system for monitoring industrial equipment, recording equipment failures, managing maintenance activities, and maintaining the operational history of industrial assets.

The platform models a simplified industrial maintenance environment in which machines are equipped with sensors, sensor measurements are collected over time, failures may occur, and maintenance work is performed to restore or preserve equipment operation.

The initial version of the system focuses on the core maintenance domain. More advanced capabilities such as real-time telemetry processing, predictive maintenance, event streaming, anomaly detection, and machine learning will be introduced in later stages.

---

## 2. Domain Scope

The core domain includes the following areas:

### Asset Management

The system manages industrial machines that represent physical equipment operating in a factory or industrial environment.

Each machine has its own identity, operational state, technical information, and maintenance history.

### Sensor Management

Machines may contain multiple sensors.

Sensors measure operational characteristics of equipment such as:

* temperature
* vibration
* pressure
* rotational speed
* electrical current

Sensor measurements are represented as time-series data associated with a specific sensor.

### Failure Management

Machines may experience failures during operation.

A failure represents a detected or reported problem associated with a machine.

Failures may require maintenance work and may result in the creation of a work order.

### Maintenance Management

Maintenance activities are performed on machines to inspect, repair, or service equipment.

The platform maintains records of completed maintenance activities so that the historical maintenance state of a machine can be reconstructed.

### Work Order Management

A work order represents a maintenance task that needs to be performed on a machine.

A work order has its own lifecycle and may progress through several states, for example:

```text
OPEN → ASSIGNED → IN_PROGRESS → COMPLETED
```

A work order may also be cancelled depending on its current state.

---

## 3. Core Domain Entities

The initial domain model contains the following main entities:

### Machine

Represents a physical industrial asset monitored and maintained by the platform.

### Sensor

Represents a physical or virtual sensor installed on a machine.

### SensorReading

Represents a measurement produced by a sensor at a specific point in time.

### Failure

Represents a malfunction or abnormal condition detected on a machine.

### WorkOrder

Represents maintenance work that needs to be performed on a machine.

### MaintenanceRecord

Represents historical information about completed maintenance activity.

### User

Represents a person interacting with the platform.

Different users may have different responsibilities and permissions within the system.

---

## 4. High-Level Domain Relationships

The main relationships between domain entities are:


Machine
 ├── has Sensors
 │     └── produce SensorReadings
 │
 ├── experiences Failures
 │     └── may create WorkOrders
 │
 ├── receives WorkOrders
 │
 └── has MaintenanceRecords


At a high level:

* A machine may have multiple sensors.
* A sensor belongs to one machine.
* A sensor may produce many sensor readings.
* A machine may experience multiple failures.
* A failure belongs to a specific machine.
* A failure may result in a work order.
* A machine may have multiple work orders.
* Completed maintenance activities are stored as maintenance records.
* Users interact with work orders and maintenance processes according to their permissions.

---

## 5. Core Business Concepts

### Equipment State

Machines have an operational state that represents their current condition.

Possible states may include concepts such as:


OPERATIONAL
UNDER_MAINTENANCE
OUT_OF_SERVICE
DECOMMISSIONED


The exact lifecycle and allowed state transitions will be defined separately.

### Work Order Lifecycle

Work orders represent executable maintenance tasks.

Their state changes must follow defined business rules rather than allowing arbitrary status changes.

Example:


OPEN
  ↓
ASSIGNED
  ↓
IN_PROGRESS
  ↓
COMPLETED


Alternative transitions such as cancellation may also exist.

### Historical Data

The system should preserve historical information about:

* sensor measurements
* machine failures
* work orders
* completed maintenance activities

This historical data will later support analytics and predictive maintenance.

---

## 6. Core Domain Rules

The following high-level rules apply to the initial system:

* Every sensor must belong to an existing machine.
* Every sensor reading must belong to an existing sensor.
* Every failure must be associated with a machine.
* Every work order must reference the machine on which maintenance will be performed.
* Work order status transitions must follow the defined lifecycle.
* Completed maintenance activities must remain available as historical records.
* Decommissioned equipment must not participate in normal maintenance workflows.
* Domain state changes must be validated by business logic rather than performed arbitrarily.

More detailed invariants and validation rules will be documented separately in `business-rules.md`.

---

## 7. Initial System Boundary

The first version of the platform intentionally focuses on the core maintenance workflow.

The following capabilities are outside the initial domain scope:

* Kafka-based telemetry streaming
* distributed microservices
* machine learning inference
* predictive maintenance
* anomaly detection
* automatic failure prediction
* real-time alerting
* large-scale time-series infrastructure
* event-driven architecture

These capabilities will be introduced incrementally after the core domain model and business workflows are stable.

This allows the project to evolve naturally from a modular backend application into a more complex industrial software platform.

---

## 8. Future Domain Extensions

The domain model may later be extended with concepts such as:

* Alert
* Prediction
* MaintenanceSchedule
* MaintenancePlan
* Technician
* SparePart
* Inventory
* Factory
* ProductionLine
* MachineComponent
* EquipmentHealthScore

Future system capabilities may include:


Sensor Data
    ↓
Telemetry Processing
    ↓
Anomaly Detection
    ↓
Failure Prediction
    ↓
Maintenance Recommendation
    ↓
Work Order


These extensions should build on the existing maintenance domain rather than replace it.

---

## 9. Domain Modeling Goal

The purpose of the domain model is to provide a shared representation of the industrial maintenance system before implementation details are introduced.

The domain model should determine:

* what entities exist
* how entities relate to each other
* what states they may have
* what business rules apply
* what operations are valid
* what historical information must be preserved

Implementation decisions such as database schemas, REST endpoints, Java classes, persistence technologies, and messaging infrastructure should be derived from this model.
