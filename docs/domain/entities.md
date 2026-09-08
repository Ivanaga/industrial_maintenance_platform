# Domain Entities

This document describes the main entities of the Industrial Maintenance Platform.

For each entity, the following aspects are defined:

* purpose
* identity
* attributes
* relationships
* lifecycle
* invariants and business rules

The definitions in this document describe the **domain model** and are independent of a particular database schema or Java implementation.

---

# 1. Machine

## 1.1 Purpose

A `Machine` represents a physical industrial asset that is monitored and maintained by the platform.

Examples of machines include:

* industrial pumps
* compressors
* CNC machines
* electric motors
* robotic arms
* production equipment

A machine acts as one of the central entities of the maintenance domain.

Sensor measurements, failures, work orders, and maintenance history are associated with machines.

---

## 1.2 Identity

Every machine has a unique internal identifier:

id

The platform uses this identifier to reference the machine internally.

A machine may additionally have a real-world serial number assigned by its manufacturer or operator.


serialNumber


Unlike the internal identifier, the serial number represents the physical asset itself.

---

## 1.3 Attributes

A machine contains the following core information.

### id

Unique internal identifier of the machine.

### name

Human-readable name used inside the organization.

Example:


Cooling Pump 04


### serialNumber

Unique serial number identifying the physical machine.

Example:


PUMP-2026-00184


### manufacturer

Manufacturer of the machine.

Example:


Siemens


### model

Machine model or product designation.

Example:


SIMOTICS SD


### installationDate

Date on which the machine was installed or commissioned.

### location

Physical or logical location of the machine.

Example:


Production Hall A


More advanced location concepts such as factories, production lines, and machine positions may later become separate domain entities.

### status

Current operational state of the machine.

Possible values:


OPERATIONAL
UNDER_MAINTENANCE
OUT_OF_SERVICE
DECOMMISSIONED

---

## 1.4 Machine Status

### OPERATIONAL

The machine is available for normal operation.

### UNDER_MAINTENANCE

Maintenance work is currently being performed on the machine.

### OUT_OF_SERVICE

The machine is temporarily unavailable because of a failure, technical problem, or operational decision.

The machine may return to service after repair or inspection.

### DECOMMISSIONED

The machine has been permanently removed from normal operation.

A decommissioned machine remains in the system because its historical information must be preserved.

---

## 1.5 Lifecycle

A machine normally begins its lifecycle as:


OPERATIONAL


Its state may change during operation.

Example transitions:


OPERATIONAL
    ↓
OUT_OF_SERVICE
    ↓
UNDER_MAINTENANCE
    ↓
OPERATIONAL


A machine may also enter maintenance directly:


OPERATIONAL
    ↓
UNDER_MAINTENANCE
    ↓
OPERATIONAL


Eventually a machine may be permanently removed from service:


OPERATIONAL
    ↓
DECOMMISSIONED


or:


OUT_OF_SERVICE
    ↓
DECOMMISSIONED


`DECOMMISSIONED` is considered a terminal state in the initial version of the domain.

---

## 1.6 Relationships

A machine may have multiple associated domain objects.

### Sensors


Machine 1 ───── * Sensor


A machine may contain multiple sensors.

Every sensor belongs to exactly one machine.

---

### Failures


Machine 1 ───── * Failure


A machine may experience multiple failures over its lifetime.

Every failure belongs to exactly one machine.

---

### Work Orders


Machine 1 ───── * WorkOrder


Maintenance work may be requested for a machine through work orders.

A machine may have many work orders over time.

---

### Maintenance Records


Machine 1 ───── * MaintenanceRecord


A machine has a historical record of maintenance activities performed on it.

---

## 1.7 Business Rules

### Unique Serial Number

Every machine must have a unique serial number.

Two machine records must not represent the same physical asset.

---

### Required Identity

A machine must have:


name
serialNumber
status


These values must not be missing.

---

### Installation Date

If an installation date is provided, it must not represent an invalid future commissioning date.

---

### Valid Status

The machine status must always contain one of the supported domain states.

Arbitrary status values are not allowed.

---

### Controlled State Transitions

Machine status must not be changed arbitrarily.

Only explicitly supported state transitions are allowed.

For example:


OPERATIONAL → UNDER_MAINTENANCE


may be valid, while:


DECOMMISSIONED → OPERATIONAL


is not valid in the initial domain model.

---

### Decommissioned Machines

A decommissioned machine remains stored in the platform for historical purposes.

Historical information such as:

* sensor readings
* failures
* work orders
* maintenance records

must not be deleted simply because the machine was decommissioned.

New normal maintenance operations must not be started for a decommissioned machine.

---

## 1.8 Conceptual Representation

The entity can currently be represented conceptually as:


Machine
────────────────────────
id
name
serialNumber
manufacturer
model
installationDate
location
status


This is a domain representation.

The final Java class and database table may contain additional technical fields such as timestamps, version numbers, or persistence-related information.

---

# 2. Sensor

To be modeled.

---

# 3. SensorReading

To be modeled.

---

# 4. Failure

To be modeled.

---

# 5. WorkOrder

To be modeled.

---

# 6. MaintenanceRecord

To be modeled.

---

# 7. User

To be modeled.
