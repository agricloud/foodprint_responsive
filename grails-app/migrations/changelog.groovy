databaseChangeLog = {

	changeSet(author: "pipi (generated)", id: "1524853372739-1") {
		createTable(tableName: "batch") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "batchPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_source_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "batch_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "country", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "effect_end_date", type: "datetime")

			column(name: "expect_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "expiration_date", type: "datetime")

			column(name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_batch_id", type: "bigint")

			column(name: "manufacture_date", type: "datetime")

			column(name: "manufacture_order_id", type: "bigint")

			column(name: "manufacturer_id", type: "bigint")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "uuid", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-2") {
		createTable(tableName: "batch_box") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "batch_boxPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "accumulation_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "auto_split", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "form_level", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "kanban_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "last_form_date", type: "datetime")

			column(name: "last_in_date", type: "datetime")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "split_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-3") {
		createTable(tableName: "batch_box_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "batch_box_detPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_box_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_box_det_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "form_date", type: "datetime")

			column(name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-4") {
		createTable(tableName: "batch_operation") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "batch_operatiPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "end_date", type: "datetime")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "item_stage_id", type: "bigint")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "operation_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "operator_id", type: "bigint")

			column(name: "remark", type: "varchar(255)")

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "start_date", type: "datetime")

			column(name: "supplier_id", type: "bigint")

			column(name: "workstation_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-5") {
		createTable(tableName: "batch_operation_realtime_record") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "batch_operatiPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_operation_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "check_in_date", type: "datetime")

			column(name: "check_in_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "check_out_date", type: "datetime")

			column(name: "check_out_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "record_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "rework_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "scrap_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "short_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "surplus_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "transfer_order_det_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-6") {
		createTable(tableName: "batch_realtime_record") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "batch_realtimPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_operation_id", type: "bigint")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "due_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "expect_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "operated_time", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "output_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "picking_status", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "priority", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "release_date", type: "datetime")

			column(name: "rework_operation_id", type: "bigint")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-7") {
		createTable(tableName: "batch_report_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "batch_report_PK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_operation_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "report_param_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "value", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-8") {
		createTable(tableName: "bill_of_material") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "bill_of_materPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_order_type_name_id", type: "bigint")

			column(name: "remark", type: "varchar(255)")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "updated_date", type: "datetime") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-9") {
		createTable(tableName: "bill_of_material_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "bill_of_materPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "denominator", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "effect_end_date", type: "datetime")

			column(name: "effect_start_date", type: "datetime")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "operation_id", type: "bigint")

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "release_order", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-10") {
		createTable(tableName: "brand") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "brandPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-11") {
		createTable(tableName: "customer") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "customerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "varchar(255)")

			column(name: "contact", type: "varchar(255)")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "email", type: "varchar(255)")

			column(name: "fax", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "shipping_address", type: "varchar(255)")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "tel", type: "varchar(255)")

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-12") {
		createTable(tableName: "customer_order") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "customer_ordePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "due_date", type: "datetime")

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "shipping_address", type: "varchar(255)")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-13") {
		createTable(tableName: "customer_order_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "customer_ordePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "convert_to_delivery_kanban", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-14") {
		createTable(tableName: "delivery_kanban") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "delivery_kanbPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_order_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "expect_shipping_date_end", type: "datetime")

			column(name: "expect_shipping_date_start", type: "datetime")

			column(name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "shipping_date", type: "datetime")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-15") {
		createTable(tableName: "employee") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "employeePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "area", type: "varchar(255)")

			column(name: "birth_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "contact", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "contact_phone_number", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "correspondence_address", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "editor", type: "varchar(255)")

			column(name: "email", type: "varchar(255)")

			column(name: "employee_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "experience", type: "varchar(255)")

			column(name: "id_number", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "introduction", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "main_work", type: "varchar(255)")

			column(name: "mobile", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "permanent_address", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "residential_address", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "tel", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-16") {
		createTable(tableName: "equipment") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "equipmentPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-17") {
		createTable(tableName: "factory") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "factoryPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "varchar(255)")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "email", type: "varchar(255)")

			column(name: "fax", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "tel", type: "varchar(255)")

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-18") {
		createTable(tableName: "inventory") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "inventoryPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-19") {
		createTable(tableName: "inventory_detail") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "inventory_detPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-20") {
		createTable(tableName: "inventory_transaction_record") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "inventory_traPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "multiplier", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "transaction_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint")

			column(name: "warehouse_location_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-21") {
		createTable(tableName: "inventory_transaction_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "inventory_traPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-22") {
		createTable(tableName: "inventory_transaction_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "inventory_traPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "in_warehouse_id", type: "bigint")

			column(name: "in_warehouse_location_id", type: "bigint")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "out_warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "out_warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-23") {
		createTable(tableName: "item") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "itemPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "brand_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "cycle_time", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "default_manufacturer_id", type: "bigint")

			column(name: "default_workstation_id", type: "bigint")

			column(name: "description", type: "varchar(255)")

			column(name: "due_days", type: "bigint")

			column(name: "editor", type: "varchar(255)")

			column(name: "effect_end_date", type: "datetime")

			column(name: "effect_start_date", type: "datetime")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "item_category_layer1_id", type: "bigint")

			column(name: "item_category_layer2_id", type: "bigint")

			column(name: "item_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "spec", type: "varchar(255)")

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "work_flow_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-24") {
		createTable(tableName: "item_category_layer1") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "item_categoryPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-25") {
		createTable(tableName: "item_category_layer2") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "item_categoryPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "item_category_layer1_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-26") {
		createTable(tableName: "item_registered_num") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "item_registerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "country", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacturer_id", type: "bigint")

			column(name: "registered_num", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-27") {
		createTable(tableName: "item_route") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "item_routePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "operation_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint")

			column(name: "workstation_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-28") {
		createTable(tableName: "item_stage") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "item_stagePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "editor", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-29") {
		createTable(tableName: "manufacture_order") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "manufacture_oPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_order_det_id", type: "bigint")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "expect_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "is_split", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint")

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "workstation_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-30") {
		createTable(tableName: "manufacture_order_change_order") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "manufacture_oPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_order_det_id", type: "bigint")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "expect_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "origin_batch_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "origin_creator", type: "varchar(255)")

			column(name: "origin_customer_order_det_id", type: "integer")

			column(name: "origin_date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "origin_editor", type: "varchar(255)")

			column(name: "origin_expect_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "origin_factory_id", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "origin_item_id", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "origin_last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "origin_status", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "origin_supplier_id", type: "integer")

			column(name: "origin_version", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "origin_workstation_id", type: "integer")

			column(name: "reason", type: "varchar(255)")

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint")

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "workstation_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-31") {
		createTable(tableName: "manufacture_order_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "manufacture_oPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "expect_picking_date", type: "datetime")

			column(name: "expect_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "operation_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "picking_date", type: "datetime")

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "release_order", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint")

			column(name: "warehouse_location_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-32") {
		createTable(tableName: "manufacturer") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "manufacturerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "varchar(255)")

			column(name: "contact", type: "varchar(255)")

			column(name: "country", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "email", type: "varchar(255)")

			column(name: "fax", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "is_supplier", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint")

			column(name: "tel", type: "varchar(255)")

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-33") {
		createTable(tableName: "material_return_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "material_retuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint")

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "workstation_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-34") {
		createTable(tableName: "material_return_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "material_retuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "material_sheet_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-35") {
		createTable(tableName: "material_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "material_sheePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint")

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "workstation_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-36") {
		createTable(tableName: "material_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "material_sheePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_operation_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "release_order", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-37") {
		createTable(tableName: "material_sheet_det_customize") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "material_sheePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "default_value", type: "varchar(255)")

			column(name: "description", type: "varchar(255)")

			column(name: "editor", type: "varchar(255)")

			column(name: "field_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint")

			column(name: "item_category_layer1_id", type: "bigint")

			column(name: "item_category_layer2_id", type: "bigint")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-38") {
		createTable(tableName: "material_sheet_det_customize_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "material_sheePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "material_sheet_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "material_sheet_det_customize_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "value", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-39") {
		createTable(tableName: "operation") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "operationPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "operation_category_layer1_id", type: "bigint")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-40") {
		createTable(tableName: "operation_category_layer1") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "operation_catPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-41") {
		createTable(tableName: "out_src_purchase_return_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "out_src_purchPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-42") {
		createTable(tableName: "out_src_purchase_return_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "out_src_purchPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "out_src_purchase_sheet_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-43") {
		createTable(tableName: "out_src_purchase_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "out_src_purchPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-44") {
		createTable(tableName: "out_src_purchase_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "out_src_purchPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-45") {
		createTable(tableName: "param") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "paramPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "default_value", type: "varchar(255)")

			column(name: "description", type: "varchar(255)")

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "lower", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "param_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)")

			column(name: "unit", type: "varchar(255)")

			column(name: "upper", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-46") {
		createTable(tableName: "purchase_return_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "purchase_retuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-47") {
		createTable(tableName: "purchase_return_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "purchase_retuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "purchase_sheet_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-48") {
		createTable(tableName: "purchase_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "purchase_sheePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "total_price", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-49") {
		createTable(tableName: "purchase_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "purchase_sheePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "price", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "total_price", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-50") {
		createTable(tableName: "report") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "reportPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "editor", type: "varchar(255)")

			column(name: "effect_end_date", type: "datetime")

			column(name: "effect_start_date", type: "datetime")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "report_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-51") {
		createTable(tableName: "report_param") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "report_paramPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "operation_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "param_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "report_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "supplier_id", type: "bigint")

			column(name: "workstation_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-52") {
		createTable(tableName: "request_map") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "request_mapPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "config_attribute", type: "longtext") {
				constraints(nullable: "false")
			}

			column(name: "http_method", type: "varchar(255)")

			column(name: "url", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-53") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-54") {
		createTable(tableName: "role_group") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "role_groupPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_group_id", type: "bigint")

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-55") {
		createTable(tableName: "role_group_role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "role_group_roPK")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "role_group_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "site_group_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-56") {
		createTable(tableName: "sale_return_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sale_return_sPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "pick_up_address", type: "varchar(255)")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-57") {
		createTable(tableName: "sale_return_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sale_return_sPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_order_det_id", type: "bigint")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sale_sheet_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-58") {
		createTable(tableName: "sale_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sale_sheetPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "shipping_address", type: "varchar(255)")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-59") {
		createTable(tableName: "sale_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sale_sheet_dePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_order_det_id", type: "bigint")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-60") {
		createTable(tableName: "site") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sitePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "activation_code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "varchar(255)")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_group_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-61") {
		createTable(tableName: "site_group") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "site_groupPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-62") {
		createTable(tableName: "stock_in_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "stock_in_sheePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "workstation_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-63") {
		createTable(tableName: "stock_in_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "stock_in_sheePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-64") {
		createTable(tableName: "supplier") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "supplierPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "address", type: "varchar(255)")

			column(name: "contact", type: "varchar(255)")

			column(name: "country", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "email", type: "varchar(255)")

			column(name: "fax", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "is_manufacturer", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacturer_id", type: "bigint")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "tel", type: "varchar(255)")

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-65") {
		createTable(tableName: "transfer_order") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "transfer_ordePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execution_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "in_supplier_id", type: "bigint")

			column(name: "in_warehouse_id", type: "bigint")

			column(name: "in_warehouse_location_id", type: "bigint")

			column(name: "in_workstation_id", type: "bigint")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacturer_order_no", type: "varchar(255)")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "out_supplier_id", type: "bigint")

			column(name: "out_warehouse_id", type: "bigint")

			column(name: "out_warehouse_location_id", type: "bigint")

			column(name: "out_workstation_id", type: "bigint")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "transfer_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "transfer_in_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "transfer_out_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-66") {
		createTable(tableName: "transfer_order_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "transfer_ordePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "equipment_hour", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "header_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "in_operation_id", type: "bigint")

			column(name: "in_sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "labor_hour", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "out_operation_id", type: "bigint")

			column(name: "out_sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "scrap_qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "spec", type: "varchar(255)")

			column(name: "type_name_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "unit", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-67") {
		createTable(tableName: "type_name") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "type_namePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "full_title", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_type", type: "varchar(255)")

			column(name: "multiplier", type: "integer")

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "running_digit", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sheet_format_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "sheet_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)")

			column(name: "transaction_type", type: "varchar(255)")

			column(name: "year_digit", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-68") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "activation_code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "email", type: "varchar(255)")

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "full_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_login_panel", type: "varchar(255)")

			column(name: "last_login_site_id", type: "bigint")

			column(name: "last_login_time", type: "datetime")

			column(name: "last_login_workstation_id", type: "bigint")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "site_group_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "username", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-69") {
		createTable(tableName: "user_role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_rolePK")
			}

			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-70") {
		createTable(tableName: "user_role_group") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_role_groPK")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "role_group_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "site_group_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-71") {
		createTable(tableName: "user_site") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_sitePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-72") {
		createTable(tableName: "warehouse") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "warehousePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "capacity", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "capacity_unit", type: "varchar(255)")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-73") {
		createTable(tableName: "warehouse_location") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "warehouse_locPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "capacity", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "capacity_unit", type: "varchar(255)")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-74") {
		createTable(tableName: "workstation") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "workstationPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "dispatch_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "factory_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(defaultValue: "-1", name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-75") {
		createTable(tableName: "workstation_realtime_record") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "workstation_rPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_operation_id", type: "bigint")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "dispatch_sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "execute_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "import_flag", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "double precision") {
				constraints(nullable: "false")
			}

			column(name: "record_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "transfer_order_det_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-382") {
		createIndex(indexName: "FK_6yoopd3uyc659q5p95cf0r9r8", tableName: "batch") {
			column(name: "manufacture_batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-383") {
		createIndex(indexName: "FK_83d9tlav2cprpc53endsi46h2", tableName: "batch") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-384") {
		createIndex(indexName: "FK_9s9oh1tw1ec95al4wo03tihm2", tableName: "batch") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-385") {
		createIndex(indexName: "FK_9vuhsmdx60u3cxd7pukhx6lpr", tableName: "batch") {
			column(name: "manufacturer_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-386") {
		createIndex(indexName: "FK_dvnadxgcj0ul5xq4h0m8uye44", tableName: "batch") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-387") {
		createIndex(indexName: "UK_ea6wjj8co7qtkcek0at693sh5", tableName: "batch", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-388") {
		createIndex(indexName: "name_uniq_1524853372519", tableName: "batch", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-389") {
		createIndex(indexName: "FK_dfjvi4vv4unxsxx9qv78nq724", tableName: "batch_box") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-390") {
		createIndex(indexName: "FK_g8dkerqybaw3lpns3tyv2jqno", tableName: "batch_box") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-391") {
		createIndex(indexName: "FK_gcy6b2xhjdfpxlho5igmtywlv", tableName: "batch_box") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-392") {
		createIndex(indexName: "FK_j1qope61b1ngj5okwa5pi1su4", tableName: "batch_box") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-393") {
		createIndex(indexName: "unique_item_id", tableName: "batch_box", unique: "true") {
			column(name: "site_id")

			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-394") {
		createIndex(indexName: "FK_b1k5gyugq2kxlt6e1cyxfty7g", tableName: "batch_box_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-395") {
		createIndex(indexName: "FK_bx0gqnxomxeehr9705lgsuvyj", tableName: "batch_box_det") {
			column(name: "batch_box_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-396") {
		createIndex(indexName: "FK_lsawapceaahtv4ldq5ixb6dwc", tableName: "batch_box_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-397") {
		createIndex(indexName: "FK_ndi1s3sjpbob7x03ux70rp2oe", tableName: "batch_box_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-398") {
		createIndex(indexName: "unique_sequence", tableName: "batch_box_det", unique: "true") {
			column(name: "site_id")

			column(name: "batch_box_id")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-399") {
		createIndex(indexName: "FK_1ufisrht767q93u6cqcn7376f", tableName: "batch_operation") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-400") {
		createIndex(indexName: "FK_4v6fhva8up6vjswg6uh8b9jto", tableName: "batch_operation") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-401") {
		createIndex(indexName: "FK_6j01jlqrf1jam8f8tdnwse12k", tableName: "batch_operation") {
			column(name: "item_stage_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-402") {
		createIndex(indexName: "FK_6xyte9ohls5hfe1ijnn83983", tableName: "batch_operation") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-403") {
		createIndex(indexName: "FK_9t9gqpqwcv9wpqh11n4p4onai", tableName: "batch_operation") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-404") {
		createIndex(indexName: "FK_g83l0pp60q6keij8co3r9rk99", tableName: "batch_operation") {
			column(name: "operator_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-405") {
		createIndex(indexName: "FK_gr2ut6t0a2rx2x96bbdk9b7lb", tableName: "batch_operation") {
			column(name: "operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-406") {
		createIndex(indexName: "unique_sequence", tableName: "batch_operation", unique: "true") {
			column(name: "site_id")

			column(name: "batch_id")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-407") {
		createIndex(indexName: "FK_4rv5kwokyasmplb7t0c57v8ux", tableName: "batch_operation_realtime_record") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-408") {
		createIndex(indexName: "FK_b2vsy9noj9pm4k5gjb5t441x1", tableName: "batch_operation_realtime_record") {
			column(name: "batch_operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-409") {
		createIndex(indexName: "FK_p4gscb000ybd5hhe8a11mma9t", tableName: "batch_operation_realtime_record") {
			column(name: "transfer_order_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-410") {
		createIndex(indexName: "FK_ppf0sjlgrxn0bxt0u4uspx924", tableName: "batch_operation_realtime_record") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-411") {
		createIndex(indexName: "unique_batch_operation_id", tableName: "batch_operation_realtime_record", unique: "true") {
			column(name: "site_id")

			column(name: "record_type")

			column(name: "batch_id")

			column(name: "batch_operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-412") {
		createIndex(indexName: "FK_ct2funl3f6otw57x4hbsn176r", tableName: "batch_realtime_record") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-413") {
		createIndex(indexName: "FK_j3f197rxplruxeq5k0ubhjwo", tableName: "batch_realtime_record") {
			column(name: "batch_operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-414") {
		createIndex(indexName: "FK_l0ni2qwh1qqsimj4ocxo1vv9d", tableName: "batch_realtime_record") {
			column(name: "rework_operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-415") {
		createIndex(indexName: "FK_qqaq92ekpj6h1dwcemyuthw8m", tableName: "batch_realtime_record") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-416") {
		createIndex(indexName: "unique_batch_id", tableName: "batch_realtime_record", unique: "true") {
			column(name: "site_id")

			column(name: "status")

			column(name: "batch_operation_id")

			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-417") {
		createIndex(indexName: "FK_178hj9gp86xfo1hg7jidwaued", tableName: "batch_report_det") {
			column(name: "report_param_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-418") {
		createIndex(indexName: "FK_bmvj1y0pntwdbsgl9xlfvicr8", tableName: "batch_report_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-419") {
		createIndex(indexName: "FK_j6pifkeegc0oghnr3okun0yfk", tableName: "batch_report_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-420") {
		createIndex(indexName: "FK_or1ehax4u29h23nvfvqcc4rts", tableName: "batch_report_det") {
			column(name: "batch_operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-421") {
		createIndex(indexName: "unique_report_param_id", tableName: "batch_report_det", unique: "true") {
			column(name: "site_id")

			column(name: "batch_operation_id")

			column(name: "report_param_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-422") {
		createIndex(indexName: "FK_3jmjeb97g3rfldehpvbnelkdn", tableName: "bill_of_material") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-423") {
		createIndex(indexName: "FK_coguq1dospfn4dkot7abutkig", tableName: "bill_of_material") {
			column(name: "manufacture_order_type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-424") {
		createIndex(indexName: "FK_yqlq3e99y17q1y9cii557lnm", tableName: "bill_of_material") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-425") {
		createIndex(indexName: "unique_item_id", tableName: "bill_of_material", unique: "true") {
			column(name: "site_id")

			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-426") {
		createIndex(indexName: "FK_4fcws8lgfoyffm43s3gm0wwtt", tableName: "bill_of_material_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-427") {
		createIndex(indexName: "FK_7wkwudi3jo7m8b3u7mo450lc4", tableName: "bill_of_material_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-428") {
		createIndex(indexName: "FK_fmo6te6mh3tef3tkmifh0dgvl", tableName: "bill_of_material_det") {
			column(name: "operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-429") {
		createIndex(indexName: "FK_sq2upapa0vxv7gxg0s6r3oij1", tableName: "bill_of_material_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-430") {
		createIndex(indexName: "unique_sequence", tableName: "bill_of_material_det", unique: "true") {
			column(name: "site_id")

			column(name: "header_id")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-431") {
		createIndex(indexName: "FK_mo8g633fstr31g0sa26t4ftr4", tableName: "brand") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-432") {
		createIndex(indexName: "unique_name", tableName: "brand", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-433") {
		createIndex(indexName: "FK_pecfdkksttjw7rx13hqgfew26", tableName: "customer") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-434") {
		createIndex(indexName: "unique_name", tableName: "customer", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-435") {
		createIndex(indexName: "FK_2dlgi8hlafqsfdtcrq75nhrb6", tableName: "customer_order") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-436") {
		createIndex(indexName: "FK_etn54r4r6r7hkwmb0ekj1tks4", tableName: "customer_order") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-437") {
		createIndex(indexName: "FK_miuaxf6sy1ge4no7vg7tvxso3", tableName: "customer_order") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-438") {
		createIndex(indexName: "FK_puwshb8ptuseofvg0vd8iktd5", tableName: "customer_order") {
			column(name: "customer_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-439") {
		createIndex(indexName: "unique_name", tableName: "customer_order", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-440") {
		createIndex(indexName: "FK_6e6knab18xyf63icavkkcr2o7", tableName: "customer_order_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-441") {
		createIndex(indexName: "FK_95jtwit63irwsp83yaao1hjxl", tableName: "customer_order_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-442") {
		createIndex(indexName: "FK_fkaiie6w5921cdpaivqxmbjtw", tableName: "customer_order_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-443") {
		createIndex(indexName: "FK_pnwpueiv5ddqf2w63at1tbuav", tableName: "customer_order_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-444") {
		createIndex(indexName: "unique_sequence", tableName: "customer_order_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-445") {
		createIndex(indexName: "FK_65f4hm7aj740h8graw13jj5hc", tableName: "delivery_kanban") {
			column(name: "customer_order_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-446") {
		createIndex(indexName: "FK_hfefcapjnbjhn46gg7fnat12a", tableName: "delivery_kanban") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-447") {
		createIndex(indexName: "FK_myv7bmk97oobie9dttt3n527o", tableName: "delivery_kanban") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-448") {
		createIndex(indexName: "FK_s532irniyrl353liapw19i2nd", tableName: "delivery_kanban") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-449") {
		createIndex(indexName: "unique_name", tableName: "delivery_kanban", unique: "true") {
			column(name: "site_id")

			column(name: "sequence")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-450") {
		createIndex(indexName: "FK_s8f616h2f8ri3twxqq0mr587u", tableName: "employee") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-451") {
		createIndex(indexName: "unique_id_number", tableName: "employee", unique: "true") {
			column(name: "site_id")

			column(name: "id_number")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-452") {
		createIndex(indexName: "unique_name", tableName: "employee", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-453") {
		createIndex(indexName: "FK_2spkb78yewkvrlohupnuahaoh", tableName: "equipment") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-454") {
		createIndex(indexName: "FK_jj0jayeng39379dk6kjrn3v17", tableName: "equipment") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-455") {
		createIndex(indexName: "unique_name", tableName: "equipment", unique: "true") {
			column(name: "site_id")

			column(name: "factory_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-456") {
		createIndex(indexName: "FK_oor1bvks6r5ji69g7ojjgh2mg", tableName: "factory") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-457") {
		createIndex(indexName: "unique_name", tableName: "factory", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-458") {
		createIndex(indexName: "FK_67nfruy96qt236hp8w2yassq0", tableName: "inventory") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-459") {
		createIndex(indexName: "FK_oh4flp8k6tpqi9bgx7oleryqd", tableName: "inventory") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-460") {
		createIndex(indexName: "FK_t4xjpic3v3ayluu40ty85imr6", tableName: "inventory") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-461") {
		createIndex(indexName: "unique_item_id", tableName: "inventory", unique: "true") {
			column(name: "site_id")

			column(name: "warehouse_id")

			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-462") {
		createIndex(indexName: "FK_4f9ju0urcn7k17a49xydjrrgo", tableName: "inventory_detail") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-463") {
		createIndex(indexName: "FK_5ofd3npot6co5yrky6k0skgup", tableName: "inventory_detail") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-464") {
		createIndex(indexName: "FK_aid81gfp1clke3k4pmrrrn1r5", tableName: "inventory_detail") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-465") {
		createIndex(indexName: "FK_i8f9scmhpx8s75ykbryujeq11", tableName: "inventory_detail") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-466") {
		createIndex(indexName: "FK_n1bfe5onwi56057ixkxiogvog", tableName: "inventory_detail") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-467") {
		createIndex(indexName: "unique_batch_id", tableName: "inventory_detail", unique: "true") {
			column(name: "site_id")

			column(name: "item_id")

			column(name: "warehouse_location_id")

			column(name: "warehouse_id")

			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-468") {
		createIndex(indexName: "FK_2d1j26s9eylc53ppmwqb0lity", tableName: "inventory_transaction_record") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-469") {
		createIndex(indexName: "FK_artatlshbwh1t8tfnqt7vblpg", tableName: "inventory_transaction_record") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-470") {
		createIndex(indexName: "FK_e5avfsgjt357rirm6amn8tijc", tableName: "inventory_transaction_record") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-471") {
		createIndex(indexName: "FK_l7jkhvl64nforabcmeswfgsfw", tableName: "inventory_transaction_record") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-472") {
		createIndex(indexName: "FK_mkoxxmvjhxgrbta0gwpgqi0ua", tableName: "inventory_transaction_record") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-473") {
		createIndex(indexName: "FK_t6epng7kduu4d7s1hj21bh0p0", tableName: "inventory_transaction_record") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-474") {
		createIndex(indexName: "unique_sequence", tableName: "inventory_transaction_record", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "multiplier")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-475") {
		createIndex(indexName: "FK_cpgkyh8v8mna0nd3m65de42f0", tableName: "inventory_transaction_sheet") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-476") {
		createIndex(indexName: "FK_ntk3t1oaiju3ik425kwd7axy1", tableName: "inventory_transaction_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-477") {
		createIndex(indexName: "FK_ssv7mua0r8u20x5flxlnacvwo", tableName: "inventory_transaction_sheet") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-478") {
		createIndex(indexName: "unique_name", tableName: "inventory_transaction_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-479") {
		createIndex(indexName: "FK_69qt0td3rcnk43lv1pniu1tpj", tableName: "inventory_transaction_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-480") {
		createIndex(indexName: "FK_7k2ui9o77chkf94vr9g1q20n5", tableName: "inventory_transaction_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-481") {
		createIndex(indexName: "FK_bb6bf9r64nr38s8gpkphidd9a", tableName: "inventory_transaction_sheet_det") {
			column(name: "out_warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-482") {
		createIndex(indexName: "FK_dpd7nuvn6aiwl29uw7i0426v9", tableName: "inventory_transaction_sheet_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-483") {
		createIndex(indexName: "FK_i9m277prqkbxta5wujiwxg436", tableName: "inventory_transaction_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-484") {
		createIndex(indexName: "FK_idif6700a13jehgjdvhsobu36", tableName: "inventory_transaction_sheet_det") {
			column(name: "in_warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-485") {
		createIndex(indexName: "FK_imob7hfo4ruspeuduiastrqsp", tableName: "inventory_transaction_sheet_det") {
			column(name: "out_warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-486") {
		createIndex(indexName: "FK_mekbcn4psaqjaaltoyaf17v23", tableName: "inventory_transaction_sheet_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-487") {
		createIndex(indexName: "FK_n5c65tm485w9py42mknm971a9", tableName: "inventory_transaction_sheet_det") {
			column(name: "in_warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-488") {
		createIndex(indexName: "unique_sequence", tableName: "inventory_transaction_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-489") {
		createIndex(indexName: "FK_3c3l4hljhu9r4m7tn4t72t1r2", tableName: "item") {
			column(name: "brand_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-490") {
		createIndex(indexName: "FK_5vb2f0a3vme9x4ul0cf6e2ndd", tableName: "item") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-491") {
		createIndex(indexName: "FK_6bbe07v9aupmv765hxx8sgmmd", tableName: "item") {
			column(name: "item_category_layer2_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-492") {
		createIndex(indexName: "FK_8xi3oeydwflh50h568wqru019", tableName: "item") {
			column(name: "default_workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-493") {
		createIndex(indexName: "FK_ke07bfnsmgps7att10l43cjxn", tableName: "item") {
			column(name: "item_category_layer1_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-494") {
		createIndex(indexName: "FK_t045iyb4cp9wg9aa8bcjw2u4", tableName: "item") {
			column(name: "default_manufacturer_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-495") {
		createIndex(indexName: "unique_name", tableName: "item", unique: "true") {
			column(name: "site_id")

			column(name: "brand_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-496") {
		createIndex(indexName: "FK_687s3ymohah04uttu6en9m096", tableName: "item_category_layer1") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-497") {
		createIndex(indexName: "unique_name", tableName: "item_category_layer1", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-498") {
		createIndex(indexName: "FK_1lm5fvp1o47r6hrf2ro8d3j51", tableName: "item_category_layer2") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-499") {
		createIndex(indexName: "FK_4ujfdf6xunxa8dh2ph18w55sl", tableName: "item_category_layer2") {
			column(name: "item_category_layer1_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-500") {
		createIndex(indexName: "unique_name", tableName: "item_category_layer2", unique: "true") {
			column(name: "site_id")

			column(name: "item_category_layer1_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-501") {
		createIndex(indexName: "FK_8lgpc6xx2gp9le5psxmxhmq3x", tableName: "item_registered_num") {
			column(name: "manufacturer_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-502") {
		createIndex(indexName: "FK_9x71gdlvnblwyg9v71274mq0j", tableName: "item_registered_num") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-503") {
		createIndex(indexName: "FK_es0h38tsn5d6m9mf1vim1mo6s", tableName: "item_registered_num") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-504") {
		createIndex(indexName: "unique_item_id", tableName: "item_registered_num", unique: "true") {
			column(name: "site_id")

			column(name: "country")

			column(name: "manufacturer_id")

			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-505") {
		createIndex(indexName: "FK_8oc6v3w0xuq7jyktb7m423kiu", tableName: "item_route") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-506") {
		createIndex(indexName: "FK_d3wcxgwksrfvn4clbknik4nuv", tableName: "item_route") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-507") {
		createIndex(indexName: "FK_p49yuf020jwx6u67v6m3g07q6", tableName: "item_route") {
			column(name: "operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-508") {
		createIndex(indexName: "FK_q4cxe8j9fkcosfnavqtt5n1yv", tableName: "item_route") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-509") {
		createIndex(indexName: "FK_t0hh1hqo8naltohifnbwfw0yk", tableName: "item_route") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-510") {
		createIndex(indexName: "unique_sequence", tableName: "item_route", unique: "true") {
			column(name: "site_id")

			column(name: "item_id")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-511") {
		createIndex(indexName: "FK_5npp8qlst4oidhm7yur50twxj", tableName: "item_stage") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-512") {
		createIndex(indexName: "FK_96ub4px8tjvrmehecf10f9j6s", tableName: "item_stage") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-513") {
		createIndex(indexName: "unique_sequence", tableName: "item_stage", unique: "true") {
			column(name: "site_id")

			column(name: "item_id")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-514") {
		createIndex(indexName: "FK_3p57r4rdydb1rqor369u36o4w", tableName: "manufacture_order") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-515") {
		createIndex(indexName: "FK_4tjf4s0w9puytdrmbhcnny5wj", tableName: "manufacture_order") {
			column(name: "customer_order_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-516") {
		createIndex(indexName: "FK_fxjve79gdjfjx9bgf5vnldylo", tableName: "manufacture_order") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-517") {
		createIndex(indexName: "FK_glmbxrbvc5xu27muuore9qjow", tableName: "manufacture_order") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-518") {
		createIndex(indexName: "FK_kygqqc3r7q9tr08n109risoix", tableName: "manufacture_order") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-519") {
		createIndex(indexName: "FK_n7xowgd0jc6slnv58j6mpevem", tableName: "manufacture_order") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-520") {
		createIndex(indexName: "FK_o0ylpqt2xk6hfdh6a601xeqlg", tableName: "manufacture_order") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-521") {
		createIndex(indexName: "UK_b00oep11obrlo6ta9wdoyw5en", tableName: "manufacture_order", unique: "true") {
			column(name: "batch_name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-522") {
		createIndex(indexName: "batch_name_uniq_1524853372574", tableName: "manufacture_order", unique: "true") {
			column(name: "batch_name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-523") {
		createIndex(indexName: "unique_name", tableName: "manufacture_order", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-524") {
		createIndex(indexName: "FK_31127iice3a6wwa8xs7qug9e8", tableName: "manufacture_order_change_order") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-525") {
		createIndex(indexName: "FK_rb3dprm6yho8g47np040lm89f", tableName: "manufacture_order_change_order") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-526") {
		createIndex(indexName: "FK_rdurbq1re549w8jqhb1dd5poq", tableName: "manufacture_order_change_order") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-527") {
		createIndex(indexName: "FK_rm96748d4k51nlbv0hb3uwl6x", tableName: "manufacture_order_change_order") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-528") {
		createIndex(indexName: "FK_sfsen1p67yre6ussc6jqed36r", tableName: "manufacture_order_change_order") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-529") {
		createIndex(indexName: "FK_sm0ths3m32vl25nomey9ddhvs", tableName: "manufacture_order_change_order") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-530") {
		createIndex(indexName: "FK_t4b0hfqylg5mlnomysfrj73fl", tableName: "manufacture_order_change_order") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-531") {
		createIndex(indexName: "FK_tnhid0nst40ciwfppf7nbufwd", tableName: "manufacture_order_change_order") {
			column(name: "customer_order_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-532") {
		createIndex(indexName: "unique_sequence", tableName: "manufacture_order_change_order", unique: "true") {
			column(name: "site_id")

			column(name: "name")

			column(name: "type_name_id")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-533") {
		createIndex(indexName: "FK_1jlmrg9lcsldsdygcj0yjrwnh", tableName: "manufacture_order_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-534") {
		createIndex(indexName: "FK_4ca84505chi4den7hi133hvf", tableName: "manufacture_order_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-535") {
		createIndex(indexName: "FK_7ke59f13fpg255w8bkanxf1oa", tableName: "manufacture_order_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-536") {
		createIndex(indexName: "FK_8aj5wbrh9pdnv5hyqqphkuydb", tableName: "manufacture_order_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-537") {
		createIndex(indexName: "FK_cvsik3npcnxrty6er9hxghpbk", tableName: "manufacture_order_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-538") {
		createIndex(indexName: "FK_gvabfa0eil0guyc869y5w7cyj", tableName: "manufacture_order_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-539") {
		createIndex(indexName: "FK_m1syhxx05gg50nfiwtx9muq03", tableName: "manufacture_order_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-540") {
		createIndex(indexName: "FK_soo4ed0way3rusa54qwywy933", tableName: "manufacture_order_det") {
			column(name: "operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-541") {
		createIndex(indexName: "unique_name", tableName: "manufacture_order_det", unique: "true") {
			column(name: "site_id")

			column(name: "operation_id")

			column(name: "item_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-542") {
		createIndex(indexName: "FK_fv414uryske23wuuilug0ss8v", tableName: "manufacturer") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-543") {
		createIndex(indexName: "FK_lggiwe6v1329eb10hf6gmm53n", tableName: "manufacturer") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-544") {
		createIndex(indexName: "unique_name", tableName: "manufacturer", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-545") {
		createIndex(indexName: "FK_9hyoqt23ae0sye7245dgxngsv", tableName: "material_return_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-546") {
		createIndex(indexName: "FK_eateg4slsbsoemla2bf1qxiex", tableName: "material_return_sheet") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-547") {
		createIndex(indexName: "FK_eg8dnbjl0chnxp0eoirgd7xp8", tableName: "material_return_sheet") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-548") {
		createIndex(indexName: "FK_eooivid4thixyc8aav96b1a92", tableName: "material_return_sheet") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-549") {
		createIndex(indexName: "FK_k4g1va335840ymps6qix8tedr", tableName: "material_return_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-550") {
		createIndex(indexName: "unique_name", tableName: "material_return_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-551") {
		createIndex(indexName: "FK_1wtrh98fq8709g6mwe2uje4qr", tableName: "material_return_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-552") {
		createIndex(indexName: "FK_4knmq3fmudn60rq2k7prbh4up", tableName: "material_return_sheet_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-553") {
		createIndex(indexName: "FK_4lo1s5th5aaxwj5v420l39j9s", tableName: "material_return_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-554") {
		createIndex(indexName: "FK_b7najufn1tpoulwpyug33682i", tableName: "material_return_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-555") {
		createIndex(indexName: "FK_f3wxg8jj0gjgj579li81hj78d", tableName: "material_return_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-556") {
		createIndex(indexName: "FK_l7tq87qxyu1drkn0asmy12qyf", tableName: "material_return_sheet_det") {
			column(name: "material_sheet_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-557") {
		createIndex(indexName: "FK_ld8dglwc44x82gycoro22ctlp", tableName: "material_return_sheet_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-558") {
		createIndex(indexName: "FK_p7hk5x18jl0ots1e03obyop7f", tableName: "material_return_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-559") {
		createIndex(indexName: "FK_qjlg9knetp1gyplvwuyg6xtla", tableName: "material_return_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-560") {
		createIndex(indexName: "unique_sequence", tableName: "material_return_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-561") {
		createIndex(indexName: "FK_70ppu235dyvyldg20xkpeqjk4", tableName: "material_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-562") {
		createIndex(indexName: "FK_728r3cgkp0mhij23u0gtpjqjt", tableName: "material_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-563") {
		createIndex(indexName: "FK_76y2ug9bwh0ejdvgjof0587ag", tableName: "material_sheet") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-564") {
		createIndex(indexName: "FK_jreuatmv4xgh39k160kdvi65f", tableName: "material_sheet") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-565") {
		createIndex(indexName: "FK_pevfhbn2p9erh2skito6xyeqo", tableName: "material_sheet") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-566") {
		createIndex(indexName: "unique_name", tableName: "material_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-567") {
		createIndex(indexName: "FK_23k3is2mvetkoq15j0q5mcxil", tableName: "material_sheet_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-568") {
		createIndex(indexName: "FK_27cvqjd6l63m2yyto2yed71qy", tableName: "material_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-569") {
		createIndex(indexName: "FK_2id71ew7p3bj7ewsd87sg0afx", tableName: "material_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-570") {
		createIndex(indexName: "FK_8ib3t4fqsib82w33qoa3e34hd", tableName: "material_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-571") {
		createIndex(indexName: "FK_9vn3i4pbcwe7dcqsb0xb455vq", tableName: "material_sheet_det") {
			column(name: "batch_operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-572") {
		createIndex(indexName: "FK_araqosqcdwhf5yfme9wk9hao9", tableName: "material_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-573") {
		createIndex(indexName: "FK_ogjqp2sonyris03oxwrr868r6", tableName: "material_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-574") {
		createIndex(indexName: "FK_ohyueaig1aqvuyhh5k9f1p0st", tableName: "material_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-575") {
		createIndex(indexName: "FK_p1vavp4yrb0rwpjlc4hbwff4b", tableName: "material_sheet_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-576") {
		createIndex(indexName: "unique_sequence", tableName: "material_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-577") {
		createIndex(indexName: "FK_a0gruem9fk2edvmhir3avu29x", tableName: "material_sheet_det_customize") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-578") {
		createIndex(indexName: "FK_aqu03t1y3uo90f7vyu6q964mt", tableName: "material_sheet_det_customize") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-579") {
		createIndex(indexName: "FK_g6u0c0vy9xr3f3w9p3r6mp1t6", tableName: "material_sheet_det_customize") {
			column(name: "item_category_layer2_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-580") {
		createIndex(indexName: "FK_omvmn7h2bfguxswk33b9u517w", tableName: "material_sheet_det_customize") {
			column(name: "item_category_layer1_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-581") {
		createIndex(indexName: "unique_title", tableName: "material_sheet_det_customize", unique: "true") {
			column(name: "site_id")

			column(name: "item_category_layer2_id")

			column(name: "item_category_layer1_id")

			column(name: "item_id")

			column(name: "title")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-582") {
		createIndex(indexName: "FK_2ibdnrenn7qt7ifo88btkxpve", tableName: "material_sheet_det_customize_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-583") {
		createIndex(indexName: "FK_ey0jtloloubutthulvmf8xvwy", tableName: "material_sheet_det_customize_det") {
			column(name: "material_sheet_det_customize_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-584") {
		createIndex(indexName: "FK_hwj99ye9lnbrt1ap6vgmu0s0m", tableName: "material_sheet_det_customize_det") {
			column(name: "material_sheet_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-585") {
		createIndex(indexName: "unique_material_sheet_det_customize_id", tableName: "material_sheet_det_customize_det", unique: "true") {
			column(name: "site_id")

			column(name: "material_sheet_det_id")

			column(name: "material_sheet_det_customize_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-586") {
		createIndex(indexName: "FK_l92bc2xurux48mw2ps0ixfe8c", tableName: "operation") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-587") {
		createIndex(indexName: "FK_t37g9snkens7yvuq9p1d29jrc", tableName: "operation") {
			column(name: "operation_category_layer1_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-588") {
		createIndex(indexName: "unique_name", tableName: "operation", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-589") {
		createIndex(indexName: "FK_7db3ihw77ylnv5a48gq8evtn7", tableName: "operation_category_layer1") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-590") {
		createIndex(indexName: "unique_name", tableName: "operation_category_layer1", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-591") {
		createIndex(indexName: "FK_2icwyhlyb8lam31g52m8f2s6g", tableName: "out_src_purchase_return_sheet") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-592") {
		createIndex(indexName: "FK_ks8kpya7a0w5qrf83g59byosu", tableName: "out_src_purchase_return_sheet") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-593") {
		createIndex(indexName: "FK_lbue58awslloym9xoljnmwv8v", tableName: "out_src_purchase_return_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-594") {
		createIndex(indexName: "FK_ltemmn0tudmrffrr4782tskb9", tableName: "out_src_purchase_return_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-595") {
		createIndex(indexName: "unique_name", tableName: "out_src_purchase_return_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-596") {
		createIndex(indexName: "FK_48c8d3u614mbfxpvsx3brmjwl", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-597") {
		createIndex(indexName: "FK_6rdlv59c8awwj5n6g5li1avnq", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-598") {
		createIndex(indexName: "FK_7f4pb1802730lpekfflsau9bj", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-599") {
		createIndex(indexName: "FK_d2qq1wbfxb4p1gv74s25k9lle", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "out_src_purchase_sheet_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-600") {
		createIndex(indexName: "FK_gedybypg4ol2ialhballg3tb", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-601") {
		createIndex(indexName: "FK_hganudimjlp4u5pfcoglmy90s", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-602") {
		createIndex(indexName: "FK_k6cp949nagxnom1mvwijdxjbj", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-603") {
		createIndex(indexName: "FK_ptjnw2d5ammwxe6hx88qfhevo", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-604") {
		createIndex(indexName: "FK_rjk2abcxouu2l5ohege4kaj78", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-605") {
		createIndex(indexName: "unique_sequence", tableName: "out_src_purchase_return_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-606") {
		createIndex(indexName: "FK_2dgoc8thchrmenkgy7xymuive", tableName: "out_src_purchase_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-607") {
		createIndex(indexName: "FK_3j2g9t9kn4cllns4s0sphaqi9", tableName: "out_src_purchase_sheet") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-608") {
		createIndex(indexName: "FK_5ilokt73f2h4cfrc61tmootyt", tableName: "out_src_purchase_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-609") {
		createIndex(indexName: "FK_kgcm39x817bdeguhufcnl2m2j", tableName: "out_src_purchase_sheet") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-610") {
		createIndex(indexName: "unique_name", tableName: "out_src_purchase_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-611") {
		createIndex(indexName: "FK_2hutos9sxefbgaoy4qimu7oyb", tableName: "out_src_purchase_sheet_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-612") {
		createIndex(indexName: "FK_48bmlr3igv2c6ttbsjwmthd51", tableName: "out_src_purchase_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-613") {
		createIndex(indexName: "FK_6nkiryewbsd3p5sjwjdg9rf3r", tableName: "out_src_purchase_sheet_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-614") {
		createIndex(indexName: "FK_6x611dump1hgq67fa7l83vyan", tableName: "out_src_purchase_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-615") {
		createIndex(indexName: "FK_96sponget5fu9wg5q4i6v9flv", tableName: "out_src_purchase_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-616") {
		createIndex(indexName: "FK_bnp8v27nk6y3kl91oleovj4co", tableName: "out_src_purchase_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-617") {
		createIndex(indexName: "FK_f4h940rg3q31xfp4s8v3f4rj3", tableName: "out_src_purchase_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-618") {
		createIndex(indexName: "FK_rt8haln2yqegc3in5vbw76uw7", tableName: "out_src_purchase_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-619") {
		createIndex(indexName: "unique_sequence", tableName: "out_src_purchase_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-620") {
		createIndex(indexName: "FK_15g7tef40v90spx4d106820th", tableName: "param") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-621") {
		createIndex(indexName: "unique_name", tableName: "param", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-622") {
		createIndex(indexName: "FK_9fith25pugkqauuul08cx7kd1", tableName: "purchase_return_sheet") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-623") {
		createIndex(indexName: "FK_e79hkoh5dqu1nyscghvvmn5dr", tableName: "purchase_return_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-624") {
		createIndex(indexName: "FK_erm7h6ht7pswdg9os3p9mrwy7", tableName: "purchase_return_sheet") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-625") {
		createIndex(indexName: "FK_fd2q4ti003nq4chrut7vb95ys", tableName: "purchase_return_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-626") {
		createIndex(indexName: "unique_name", tableName: "purchase_return_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-627") {
		createIndex(indexName: "FK_8odcr53h4wd1u75wd3gm4hn9d", tableName: "purchase_return_sheet_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-628") {
		createIndex(indexName: "FK_9dah6ath8kajxm6ohslykwb78", tableName: "purchase_return_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-629") {
		createIndex(indexName: "FK_c8fivfrvqxsnr7flgcpy73i", tableName: "purchase_return_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-630") {
		createIndex(indexName: "FK_dmx7prw1rbhdcde7l4dnqeptn", tableName: "purchase_return_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-631") {
		createIndex(indexName: "FK_gq1ybig81hkii0m8menhns4ok", tableName: "purchase_return_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-632") {
		createIndex(indexName: "FK_lg1k6puu45sv67bk86b9ocpsa", tableName: "purchase_return_sheet_det") {
			column(name: "purchase_sheet_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-633") {
		createIndex(indexName: "FK_p6rmce8n5yvhwd8hvev9agf8c", tableName: "purchase_return_sheet_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-634") {
		createIndex(indexName: "FK_qnprmp2cd6oc64rl7sclxwc70", tableName: "purchase_return_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-635") {
		createIndex(indexName: "unique_sequence", tableName: "purchase_return_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-636") {
		createIndex(indexName: "FK_1r42oga09nc1fr1qhdvjkr7rw", tableName: "purchase_sheet") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-637") {
		createIndex(indexName: "FK_42ojn3gcorf7spwa63bbpn1iu", tableName: "purchase_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-638") {
		createIndex(indexName: "FK_88rxqca1asjjm5q8aqb6s00s8", tableName: "purchase_sheet") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-639") {
		createIndex(indexName: "FK_r7qbnd90dpqlewd0a5oni4dmm", tableName: "purchase_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-640") {
		createIndex(indexName: "unique_name", tableName: "purchase_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-641") {
		createIndex(indexName: "FK_845oq6erfxvtfiobyu2adr4na", tableName: "purchase_sheet_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-642") {
		createIndex(indexName: "FK_9poerijv9bicfso6jh7h5jkyg", tableName: "purchase_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-643") {
		createIndex(indexName: "FK_b9nvoe5ifvxiwwyf2575f5i4e", tableName: "purchase_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-644") {
		createIndex(indexName: "FK_hdm7edt9r8811m0ccaflua0v1", tableName: "purchase_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-645") {
		createIndex(indexName: "FK_pa7qqge8k6ok67cn3034sdouh", tableName: "purchase_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-646") {
		createIndex(indexName: "FK_pvsiqjmhpev4nf1w2ksk4i6wj", tableName: "purchase_sheet_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-647") {
		createIndex(indexName: "FK_sghaj74l429opjmkeq96iyfj1", tableName: "purchase_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-648") {
		createIndex(indexName: "unique_sequence", tableName: "purchase_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-649") {
		createIndex(indexName: "FK_fy5rh80u3fa7s7wnxyr4vsd3q", tableName: "report") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-650") {
		createIndex(indexName: "FK_i6gj27jfyup8d9l0luj2s201n", tableName: "report") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-651") {
		createIndex(indexName: "unique_name", tableName: "report", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-652") {
		createIndex(indexName: "FK_53hm28b9vsw61gyv9clyfhrmy", tableName: "report_param") {
			column(name: "param_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-653") {
		createIndex(indexName: "FK_8nntnio4s82tqo3nld7b6oxwt", tableName: "report_param") {
			column(name: "report_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-654") {
		createIndex(indexName: "FK_8ugbue4eb0juouc7n0iyio4vt", tableName: "report_param") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-655") {
		createIndex(indexName: "FK_fsaeq0e4bx906sok26c2rpx2t", tableName: "report_param") {
			column(name: "operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-656") {
		createIndex(indexName: "FK_meok1csnvjeufd1skhkenfuvf", tableName: "report_param") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-657") {
		createIndex(indexName: "FK_njoam4qk8reinxa5qtvotvr10", tableName: "report_param") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-658") {
		createIndex(indexName: "unique_param_id", tableName: "report_param", unique: "true") {
			column(name: "site_id")

			column(name: "supplier_id")

			column(name: "workstation_id")

			column(name: "operation_id")

			column(name: "report_id")

			column(name: "param_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-659") {
		createIndex(indexName: "unique_url", tableName: "request_map", unique: "true") {
			column(name: "http_method")

			column(name: "url")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-660") {
		createIndex(indexName: "UK_irsamgnera6angm0prq1kemt2", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-661") {
		createIndex(indexName: "authority_uniq_1524853372606", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-662") {
		createIndex(indexName: "FK_ny28jwhq73twly1uekrcbe804", tableName: "role_group") {
			column(name: "site_group_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-663") {
		createIndex(indexName: "unique_name", tableName: "role_group", unique: "true") {
			column(name: "site_group_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-664") {
		createIndex(indexName: "FK_cqr9g8iosupkylg4fw10paumq", tableName: "role_group_role") {
			column(name: "role_group_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-665") {
		createIndex(indexName: "FK_dlrjbyj2jhsprfuoe5gbcgui4", tableName: "role_group_role") {
			column(name: "site_group_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-666") {
		createIndex(indexName: "FK_dxc3snhixkg9qn3c46p6hyjli", tableName: "role_group_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-667") {
		createIndex(indexName: "unique_role_group_id", tableName: "role_group_role", unique: "true") {
			column(name: "role_id")

			column(name: "role_group_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-668") {
		createIndex(indexName: "FK_c8xoir02a5fov7mmii0r1cpiq", tableName: "sale_return_sheet") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-669") {
		createIndex(indexName: "FK_kbbgej0ewnalcxyu639tetjb7", tableName: "sale_return_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-670") {
		createIndex(indexName: "FK_q9ncrk91oc96xcu9fgtocjaiq", tableName: "sale_return_sheet") {
			column(name: "customer_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-671") {
		createIndex(indexName: "FK_qpci4sh7d3bjyrxwnvb3412x5", tableName: "sale_return_sheet") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-672") {
		createIndex(indexName: "unique_name", tableName: "sale_return_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-673") {
		createIndex(indexName: "FK_292o14arpg1kq827914cq2ncc", tableName: "sale_return_sheet_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-674") {
		createIndex(indexName: "FK_6husryq2shk2i9w0h4jcc1wr4", tableName: "sale_return_sheet_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-675") {
		createIndex(indexName: "FK_76lh4ld0cnr4l2ucvw3n8sboh", tableName: "sale_return_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-676") {
		createIndex(indexName: "FK_7t14hqgeapgeuoejltiogqd37", tableName: "sale_return_sheet_det") {
			column(name: "sale_sheet_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-677") {
		createIndex(indexName: "FK_8qu1dnylrl0uppyh5clafig8r", tableName: "sale_return_sheet_det") {
			column(name: "customer_order_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-678") {
		createIndex(indexName: "FK_9ije3845f3n3fdsmshcpfocrq", tableName: "sale_return_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-679") {
		createIndex(indexName: "FK_cbcy2rl8mvtukm8kos2fxua9e", tableName: "sale_return_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-680") {
		createIndex(indexName: "FK_edm035ijwvmqa91jajciryyna", tableName: "sale_return_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-681") {
		createIndex(indexName: "FK_nbv8j4mwsp94kbtdspw8fryc8", tableName: "sale_return_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-682") {
		createIndex(indexName: "unique_sequence", tableName: "sale_return_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-683") {
		createIndex(indexName: "FK_5j6lrgh3mr6t4bdctu7pj5uku", tableName: "sale_sheet") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-684") {
		createIndex(indexName: "FK_de6sv7k0tqis1bl9oparwkfih", tableName: "sale_sheet") {
			column(name: "customer_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-685") {
		createIndex(indexName: "FK_khm1als5yqy2ukotvon7r62dw", tableName: "sale_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-686") {
		createIndex(indexName: "FK_r479xiyuy9mc2kqf0sfqdwk03", tableName: "sale_sheet") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-687") {
		createIndex(indexName: "unique_name", tableName: "sale_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-688") {
		createIndex(indexName: "FK_19oiydb6st3nt2lvsphatfl03", tableName: "sale_sheet_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-689") {
		createIndex(indexName: "FK_984k2sm9u5n91og2ojekrv4rt", tableName: "sale_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-690") {
		createIndex(indexName: "FK_b9qncquulkfjra6escotau1ym", tableName: "sale_sheet_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-691") {
		createIndex(indexName: "FK_efi9nxpexdmlw904dfp2kegqf", tableName: "sale_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-692") {
		createIndex(indexName: "FK_if5b74ugvau319fw91xnjsflx", tableName: "sale_sheet_det") {
			column(name: "customer_order_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-693") {
		createIndex(indexName: "FK_krmrobivnaqafhcwpm30vj5c7", tableName: "sale_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-694") {
		createIndex(indexName: "FK_oioqwy7ohchlnl0m3d86aij62", tableName: "sale_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-695") {
		createIndex(indexName: "FK_tjhnq978hj8hj99djkrhc8hs5", tableName: "sale_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-696") {
		createIndex(indexName: "unique_sequence", tableName: "sale_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-697") {
		createIndex(indexName: "FK_6shh0pw8icn4sc7s3usp1ph59", tableName: "site") {
			column(name: "site_group_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-698") {
		createIndex(indexName: "UK_f9iil6uu8d9ohutpr2irlpvio", tableName: "site", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-699") {
		createIndex(indexName: "name_uniq_1524853372613", tableName: "site", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-700") {
		createIndex(indexName: "UK_t60q0metnp8lohm7bj9grqbdg", tableName: "site_group", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-701") {
		createIndex(indexName: "name_uniq_1524853372613", tableName: "site_group", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-702") {
		createIndex(indexName: "FK_m5nfkmsderpiex0ua0pg8uo1e", tableName: "stock_in_sheet") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-703") {
		createIndex(indexName: "FK_mgmdbg5383orrwqycc79mc544", tableName: "stock_in_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-704") {
		createIndex(indexName: "FK_p95iorag6x2x81tmne0typ6xg", tableName: "stock_in_sheet") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-705") {
		createIndex(indexName: "FK_qf95nflime2762babrcr9bp9e", tableName: "stock_in_sheet") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-706") {
		createIndex(indexName: "unique_name", tableName: "stock_in_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-707") {
		createIndex(indexName: "FK_2nqqu1je6cb3dniwx37fvjwfw", tableName: "stock_in_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-708") {
		createIndex(indexName: "FK_3eyjeo2traxomkfhamefy19ir", tableName: "stock_in_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-709") {
		createIndex(indexName: "FK_b4rnggdi89dok3sjtpruk4hx1", tableName: "stock_in_sheet_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-710") {
		createIndex(indexName: "FK_b8tj4bctiy5a29yh7m0nqh8fl", tableName: "stock_in_sheet_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-711") {
		createIndex(indexName: "FK_fffmvuc6jpykc4is5nk47fyqk", tableName: "stock_in_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-712") {
		createIndex(indexName: "FK_huaa3a9cgsh5w9dajby89ivam", tableName: "stock_in_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-713") {
		createIndex(indexName: "FK_tdqsevurpl949qt8gucioc1qt", tableName: "stock_in_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-714") {
		createIndex(indexName: "FK_tp5711hqog6ea8harif89jj68", tableName: "stock_in_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-715") {
		createIndex(indexName: "unique_sequence", tableName: "stock_in_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-716") {
		createIndex(indexName: "FK_28blf3skobr5hc2f07ve2vqyo", tableName: "supplier") {
			column(name: "manufacturer_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-717") {
		createIndex(indexName: "FK_s33bm3bnb1q0g0t2j0sjo3odm", tableName: "supplier") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-718") {
		createIndex(indexName: "unique_name", tableName: "supplier", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-719") {
		createIndex(indexName: "FK_1jsr043d7562dqgs87vp6t5c1", tableName: "transfer_order") {
			column(name: "out_warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-720") {
		createIndex(indexName: "FK_3x6gfe5d8llhtj87jll18upr1", tableName: "transfer_order") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-721") {
		createIndex(indexName: "FK_8ytji4iwf6tswi8v9d5nxdnji", tableName: "transfer_order") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-722") {
		createIndex(indexName: "FK_93f7nst7m264s6ypahcfim78b", tableName: "transfer_order") {
			column(name: "in_supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-723") {
		createIndex(indexName: "FK_9d2cu52sphx3vpxy9vlqv3tmw", tableName: "transfer_order") {
			column(name: "out_supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-724") {
		createIndex(indexName: "FK_9rn3rvqoi78o4gkd8sq18g417", tableName: "transfer_order") {
			column(name: "out_workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-725") {
		createIndex(indexName: "FK_bi9e4m30jfdjyw71mv98csrre", tableName: "transfer_order") {
			column(name: "in_warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-726") {
		createIndex(indexName: "FK_d0xfmj6cc54aoah4lwflnxd41", tableName: "transfer_order") {
			column(name: "in_workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-727") {
		createIndex(indexName: "FK_htcmysq6ny7gsccfjkajc1cla", tableName: "transfer_order") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-728") {
		createIndex(indexName: "FK_jn01trs9t1snkw57f1tlgbyof", tableName: "transfer_order") {
			column(name: "out_warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-729") {
		createIndex(indexName: "FK_oih0j2p6uip1at9vhi080fr00", tableName: "transfer_order") {
			column(name: "in_warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-730") {
		createIndex(indexName: "unique_name", tableName: "transfer_order", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-731") {
		createIndex(indexName: "FK_185rqqn54archxkfyr3abhjjv", tableName: "transfer_order_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-732") {
		createIndex(indexName: "FK_2m66bl5ao1j5bqqhbs3eejmtp", tableName: "transfer_order_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-733") {
		createIndex(indexName: "FK_a3j9k0ibyoth0dek47tofaf8n", tableName: "transfer_order_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-734") {
		createIndex(indexName: "FK_cscxmn0dk91fpdycbtpoa24se", tableName: "transfer_order_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-735") {
		createIndex(indexName: "FK_cudf8v7qs8jvdiq7ktnsdohyo", tableName: "transfer_order_det") {
			column(name: "header_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-736") {
		createIndex(indexName: "FK_m014m5tr9ile05omqjnl8dsab", tableName: "transfer_order_det") {
			column(name: "type_name_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-737") {
		createIndex(indexName: "FK_orxfexhn7xt8ywsf436ixjfs4", tableName: "transfer_order_det") {
			column(name: "in_operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-738") {
		createIndex(indexName: "FK_rorw5b84ifebf0sq4261pk3m2", tableName: "transfer_order_det") {
			column(name: "out_operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-739") {
		createIndex(indexName: "unique_sequence", tableName: "transfer_order_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name_id")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-740") {
		createIndex(indexName: "FK_bnkxr4t361adppyxq7w7piwl3", tableName: "type_name") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-741") {
		createIndex(indexName: "unique_name", tableName: "type_name", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-742") {
		createIndex(indexName: "FK_4jxy1p8w6n5qakg4kmpsvhb2v", tableName: "user") {
			column(name: "site_group_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-743") {
		createIndex(indexName: "FK_a0yvgetnjbcbxq2alu33ihau3", tableName: "user") {
			column(name: "last_login_site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-744") {
		createIndex(indexName: "FK_qv1d8l5cvc8obnt91ggpsx3j7", tableName: "user") {
			column(name: "last_login_workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-745") {
		createIndex(indexName: "UK_sb8bbouer5wak8vyiiy4pf2bx", tableName: "user", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-746") {
		createIndex(indexName: "username_uniq_1524853372622", tableName: "user", unique: "true") {
			column(name: "username")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-747") {
		createIndex(indexName: "FK_apcc8lxk2xnug8377fatvbn04", tableName: "user_role") {
			column(name: "user_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-748") {
		createIndex(indexName: "FK_it77eq964jhfqtu54081ebtio", tableName: "user_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-749") {
		createIndex(indexName: "unique_user_id", tableName: "user_role", unique: "true") {
			column(name: "role_id")

			column(name: "user_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-750") {
		createIndex(indexName: "FK_9se04wabb1vif6yjcfged3s2p", tableName: "user_role_group") {
			column(name: "role_group_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-751") {
		createIndex(indexName: "FK_d9ttuc99ggo3nld2pd96x6pe7", tableName: "user_role_group") {
			column(name: "user_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-752") {
		createIndex(indexName: "FK_js6ws9c0uls1unc8sckl9d692", tableName: "user_role_group") {
			column(name: "site_group_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-753") {
		createIndex(indexName: "unique_user_id", tableName: "user_role_group", unique: "true") {
			column(name: "role_group_id")

			column(name: "user_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-754") {
		createIndex(indexName: "FK_2hoesr99ded12jw9o5foy7j63", tableName: "user_site") {
			column(name: "user_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-755") {
		createIndex(indexName: "FK_qggpk5dbgt4qtyjs8la22js9k", tableName: "user_site") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-756") {
		createIndex(indexName: "unique_user_id", tableName: "user_site", unique: "true") {
			column(name: "site_id")

			column(name: "user_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-757") {
		createIndex(indexName: "FK_jlc37t4s9vxopreicapjjresm", tableName: "warehouse") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-758") {
		createIndex(indexName: "FK_sfv52kwxoefym205pw8elwqw9", tableName: "warehouse") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-759") {
		createIndex(indexName: "unique_name", tableName: "warehouse", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-760") {
		createIndex(indexName: "FK_7nvsdwcb3ls2646cv215o7l6a", tableName: "warehouse_location") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-761") {
		createIndex(indexName: "FK_m11dap7qewyk9o6hvl24493qu", tableName: "warehouse_location") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-762") {
		createIndex(indexName: "unique_name", tableName: "warehouse_location", unique: "true") {
			column(name: "site_id")

			column(name: "warehouse_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-763") {
		createIndex(indexName: "FK_f99gjgjacu2a881ir1konfsqy", tableName: "workstation") {
			column(name: "factory_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-764") {
		createIndex(indexName: "FK_gev69fm9u03xor4390poc1nje", tableName: "workstation") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-765") {
		createIndex(indexName: "unique_name", tableName: "workstation", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-766") {
		createIndex(indexName: "FK_47e03t7r3t8np1ibeujv18wel", tableName: "workstation_realtime_record") {
			column(name: "transfer_order_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-767") {
		createIndex(indexName: "FK_faby91a81bo8whty4wpv29h1k", tableName: "workstation_realtime_record") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-768") {
		createIndex(indexName: "FK_m01inkv537034ux476rsbi4of", tableName: "workstation_realtime_record") {
			column(name: "batch_operation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-769") {
		createIndex(indexName: "FK_ns24iqwteq4epceaey2fs1gle", tableName: "workstation_realtime_record") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-770") {
		createIndex(indexName: "unique_batch_id", tableName: "workstation_realtime_record", unique: "true") {
			column(name: "site_id")

			column(name: "sequence")

			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-76") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "batch", constraintName: "FK_83d9tlav2cprpc53endsi46h2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-77") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_batch_id", baseTableName: "batch", constraintName: "FK_6yoopd3uyc659q5p95cf0r9r8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-78") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "batch", constraintName: "FK_dvnadxgcj0ul5xq4h0m8uye44", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-79") {
		addForeignKeyConstraint(baseColumnNames: "manufacturer_id", baseTableName: "batch", constraintName: "FK_9vuhsmdx60u3cxd7pukhx6lpr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacturer", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-80") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "batch", constraintName: "FK_9s9oh1tw1ec95al4wo03tihm2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-81") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "batch_box", constraintName: "FK_j1qope61b1ngj5okwa5pi1su4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-82") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "batch_box", constraintName: "FK_dfjvi4vv4unxsxx9qv78nq724", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-83") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "batch_box", constraintName: "FK_gcy6b2xhjdfpxlho5igmtywlv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-84") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "batch_box", constraintName: "FK_g8dkerqybaw3lpns3tyv2jqno", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-85") {
		addForeignKeyConstraint(baseColumnNames: "batch_box_id", baseTableName: "batch_box_det", constraintName: "FK_bx0gqnxomxeehr9705lgsuvyj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch_box", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-86") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "batch_box_det", constraintName: "FK_ndi1s3sjpbob7x03ux70rp2oe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-87") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "batch_box_det", constraintName: "FK_b1k5gyugq2kxlt6e1cyxfty7g", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-88") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "batch_box_det", constraintName: "FK_lsawapceaahtv4ldq5ixb6dwc", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-89") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "batch_operation", constraintName: "FK_1ufisrht767q93u6cqcn7376f", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-90") {
		addForeignKeyConstraint(baseColumnNames: "item_stage_id", baseTableName: "batch_operation", constraintName: "FK_6j01jlqrf1jam8f8tdnwse12k", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_stage", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-91") {
		addForeignKeyConstraint(baseColumnNames: "operation_id", baseTableName: "batch_operation", constraintName: "FK_gr2ut6t0a2rx2x96bbdk9b7lb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-92") {
		addForeignKeyConstraint(baseColumnNames: "operator_id", baseTableName: "batch_operation", constraintName: "FK_g83l0pp60q6keij8co3r9rk99", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "employee", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-93") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "batch_operation", constraintName: "FK_4v6fhva8up6vjswg6uh8b9jto", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-94") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "batch_operation", constraintName: "FK_6xyte9ohls5hfe1ijnn83983", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-95") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "batch_operation", constraintName: "FK_9t9gqpqwcv9wpqh11n4p4onai", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-96") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "batch_operation_realtime_record", constraintName: "FK_4rv5kwokyasmplb7t0c57v8ux", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-97") {
		addForeignKeyConstraint(baseColumnNames: "batch_operation_id", baseTableName: "batch_operation_realtime_record", constraintName: "FK_b2vsy9noj9pm4k5gjb5t441x1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch_operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-98") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "batch_operation_realtime_record", constraintName: "FK_ppf0sjlgrxn0bxt0u4uspx924", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-99") {
		addForeignKeyConstraint(baseColumnNames: "transfer_order_det_id", baseTableName: "batch_operation_realtime_record", constraintName: "FK_p4gscb000ybd5hhe8a11mma9t", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "transfer_order_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-100") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "batch_realtime_record", constraintName: "FK_ct2funl3f6otw57x4hbsn176r", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-101") {
		addForeignKeyConstraint(baseColumnNames: "batch_operation_id", baseTableName: "batch_realtime_record", constraintName: "FK_j3f197rxplruxeq5k0ubhjwo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch_operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-102") {
		addForeignKeyConstraint(baseColumnNames: "rework_operation_id", baseTableName: "batch_realtime_record", constraintName: "FK_l0ni2qwh1qqsimj4ocxo1vv9d", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-103") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "batch_realtime_record", constraintName: "FK_qqaq92ekpj6h1dwcemyuthw8m", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-104") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "batch_report_det", constraintName: "FK_j6pifkeegc0oghnr3okun0yfk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-105") {
		addForeignKeyConstraint(baseColumnNames: "batch_operation_id", baseTableName: "batch_report_det", constraintName: "FK_or1ehax4u29h23nvfvqcc4rts", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch_operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-106") {
		addForeignKeyConstraint(baseColumnNames: "report_param_id", baseTableName: "batch_report_det", constraintName: "FK_178hj9gp86xfo1hg7jidwaued", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "report_param", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-107") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "batch_report_det", constraintName: "FK_bmvj1y0pntwdbsgl9xlfvicr8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-108") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "bill_of_material", constraintName: "FK_yqlq3e99y17q1y9cii557lnm", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-109") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_type_name_id", baseTableName: "bill_of_material", constraintName: "FK_coguq1dospfn4dkot7abutkig", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-110") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "bill_of_material", constraintName: "FK_3jmjeb97g3rfldehpvbnelkdn", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-111") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "bill_of_material_det", constraintName: "FK_7wkwudi3jo7m8b3u7mo450lc4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "bill_of_material", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-112") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "bill_of_material_det", constraintName: "FK_4fcws8lgfoyffm43s3gm0wwtt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-113") {
		addForeignKeyConstraint(baseColumnNames: "operation_id", baseTableName: "bill_of_material_det", constraintName: "FK_fmo6te6mh3tef3tkmifh0dgvl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-114") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "bill_of_material_det", constraintName: "FK_sq2upapa0vxv7gxg0s6r3oij1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-115") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "brand", constraintName: "FK_mo8g633fstr31g0sa26t4ftr4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-116") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "customer", constraintName: "FK_pecfdkksttjw7rx13hqgfew26", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-117") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "customer_order", constraintName: "FK_puwshb8ptuseofvg0vd8iktd5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-118") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "customer_order", constraintName: "FK_miuaxf6sy1ge4no7vg7tvxso3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-119") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "customer_order", constraintName: "FK_2dlgi8hlafqsfdtcrq75nhrb6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-120") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "customer_order", constraintName: "FK_etn54r4r6r7hkwmb0ekj1tks4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-121") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "customer_order_det", constraintName: "FK_pnwpueiv5ddqf2w63at1tbuav", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-122") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "customer_order_det", constraintName: "FK_95jtwit63irwsp83yaao1hjxl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-123") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "customer_order_det", constraintName: "FK_fkaiie6w5921cdpaivqxmbjtw", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-124") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "customer_order_det", constraintName: "FK_6e6knab18xyf63icavkkcr2o7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-125") {
		addForeignKeyConstraint(baseColumnNames: "customer_order_det_id", baseTableName: "delivery_kanban", constraintName: "FK_65f4hm7aj740h8graw13jj5hc", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer_order_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-126") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "delivery_kanban", constraintName: "FK_hfefcapjnbjhn46gg7fnat12a", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-127") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "delivery_kanban", constraintName: "FK_s532irniyrl353liapw19i2nd", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-128") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "delivery_kanban", constraintName: "FK_myv7bmk97oobie9dttt3n527o", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-129") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "employee", constraintName: "FK_s8f616h2f8ri3twxqq0mr587u", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-130") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "equipment", constraintName: "FK_jj0jayeng39379dk6kjrn3v17", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-131") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "equipment", constraintName: "FK_2spkb78yewkvrlohupnuahaoh", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-132") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "factory", constraintName: "FK_oor1bvks6r5ji69g7ojjgh2mg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-133") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "inventory", constraintName: "FK_oh4flp8k6tpqi9bgx7oleryqd", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-134") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "inventory", constraintName: "FK_67nfruy96qt236hp8w2yassq0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-135") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "inventory", constraintName: "FK_t4xjpic3v3ayluu40ty85imr6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-136") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "inventory_detail", constraintName: "FK_4f9ju0urcn7k17a49xydjrrgo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-137") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "inventory_detail", constraintName: "FK_aid81gfp1clke3k4pmrrrn1r5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-138") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "inventory_detail", constraintName: "FK_5ofd3npot6co5yrky6k0skgup", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-139") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "inventory_detail", constraintName: "FK_n1bfe5onwi56057ixkxiogvog", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-140") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "inventory_detail", constraintName: "FK_i8f9scmhpx8s75ykbryujeq11", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-141") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "inventory_transaction_record", constraintName: "FK_artatlshbwh1t8tfnqt7vblpg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-142") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "inventory_transaction_record", constraintName: "FK_t6epng7kduu4d7s1hj21bh0p0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-143") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "inventory_transaction_record", constraintName: "FK_mkoxxmvjhxgrbta0gwpgqi0ua", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-144") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "inventory_transaction_record", constraintName: "FK_l7jkhvl64nforabcmeswfgsfw", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-145") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "inventory_transaction_record", constraintName: "FK_2d1j26s9eylc53ppmwqb0lity", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-146") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "inventory_transaction_record", constraintName: "FK_e5avfsgjt357rirm6amn8tijc", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-147") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "inventory_transaction_sheet", constraintName: "FK_ssv7mua0r8u20x5flxlnacvwo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-148") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "inventory_transaction_sheet", constraintName: "FK_ntk3t1oaiju3ik425kwd7axy1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-149") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "inventory_transaction_sheet", constraintName: "FK_cpgkyh8v8mna0nd3m65de42f0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-150") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "inventory_transaction_sheet_det", constraintName: "FK_7k2ui9o77chkf94vr9g1q20n5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-151") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "inventory_transaction_sheet_det", constraintName: "FK_mekbcn4psaqjaaltoyaf17v23", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "inventory_transaction_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-152") {
		addForeignKeyConstraint(baseColumnNames: "in_warehouse_id", baseTableName: "inventory_transaction_sheet_det", constraintName: "FK_n5c65tm485w9py42mknm971a9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-153") {
		addForeignKeyConstraint(baseColumnNames: "in_warehouse_location_id", baseTableName: "inventory_transaction_sheet_det", constraintName: "FK_idif6700a13jehgjdvhsobu36", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-154") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "inventory_transaction_sheet_det", constraintName: "FK_69qt0td3rcnk43lv1pniu1tpj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-155") {
		addForeignKeyConstraint(baseColumnNames: "out_warehouse_id", baseTableName: "inventory_transaction_sheet_det", constraintName: "FK_bb6bf9r64nr38s8gpkphidd9a", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-156") {
		addForeignKeyConstraint(baseColumnNames: "out_warehouse_location_id", baseTableName: "inventory_transaction_sheet_det", constraintName: "FK_imob7hfo4ruspeuduiastrqsp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-157") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "inventory_transaction_sheet_det", constraintName: "FK_i9m277prqkbxta5wujiwxg436", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-158") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "inventory_transaction_sheet_det", constraintName: "FK_dpd7nuvn6aiwl29uw7i0426v9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-159") {
		addForeignKeyConstraint(baseColumnNames: "brand_id", baseTableName: "item", constraintName: "FK_3c3l4hljhu9r4m7tn4t72t1r2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "brand", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-160") {
		addForeignKeyConstraint(baseColumnNames: "default_manufacturer_id", baseTableName: "item", constraintName: "FK_t045iyb4cp9wg9aa8bcjw2u4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacturer", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-161") {
		addForeignKeyConstraint(baseColumnNames: "default_workstation_id", baseTableName: "item", constraintName: "FK_8xi3oeydwflh50h568wqru019", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-162") {
		addForeignKeyConstraint(baseColumnNames: "item_category_layer1_id", baseTableName: "item", constraintName: "FK_ke07bfnsmgps7att10l43cjxn", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_category_layer1", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-163") {
		addForeignKeyConstraint(baseColumnNames: "item_category_layer2_id", baseTableName: "item", constraintName: "FK_6bbe07v9aupmv765hxx8sgmmd", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_category_layer2", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-164") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "item", constraintName: "FK_5vb2f0a3vme9x4ul0cf6e2ndd", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-165") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "item_category_layer1", constraintName: "FK_687s3ymohah04uttu6en9m096", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-166") {
		addForeignKeyConstraint(baseColumnNames: "item_category_layer1_id", baseTableName: "item_category_layer2", constraintName: "FK_4ujfdf6xunxa8dh2ph18w55sl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_category_layer1", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-167") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "item_category_layer2", constraintName: "FK_1lm5fvp1o47r6hrf2ro8d3j51", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-168") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "item_registered_num", constraintName: "FK_9x71gdlvnblwyg9v71274mq0j", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-169") {
		addForeignKeyConstraint(baseColumnNames: "manufacturer_id", baseTableName: "item_registered_num", constraintName: "FK_8lgpc6xx2gp9le5psxmxhmq3x", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacturer", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-170") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "item_registered_num", constraintName: "FK_es0h38tsn5d6m9mf1vim1mo6s", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-171") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "item_route", constraintName: "FK_q4cxe8j9fkcosfnavqtt5n1yv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-172") {
		addForeignKeyConstraint(baseColumnNames: "operation_id", baseTableName: "item_route", constraintName: "FK_p49yuf020jwx6u67v6m3g07q6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-173") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "item_route", constraintName: "FK_8oc6v3w0xuq7jyktb7m423kiu", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-174") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "item_route", constraintName: "FK_t0hh1hqo8naltohifnbwfw0yk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-175") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "item_route", constraintName: "FK_d3wcxgwksrfvn4clbknik4nuv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-176") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "item_stage", constraintName: "FK_5npp8qlst4oidhm7yur50twxj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-177") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "item_stage", constraintName: "FK_96ub4px8tjvrmehecf10f9j6s", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-178") {
		addForeignKeyConstraint(baseColumnNames: "customer_order_det_id", baseTableName: "manufacture_order", constraintName: "FK_4tjf4s0w9puytdrmbhcnny5wj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer_order_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-179") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "manufacture_order", constraintName: "FK_3p57r4rdydb1rqor369u36o4w", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-180") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "manufacture_order", constraintName: "FK_glmbxrbvc5xu27muuore9qjow", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-181") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "manufacture_order", constraintName: "FK_n7xowgd0jc6slnv58j6mpevem", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-182") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "manufacture_order", constraintName: "FK_o0ylpqt2xk6hfdh6a601xeqlg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-183") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "manufacture_order", constraintName: "FK_kygqqc3r7q9tr08n109risoix", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-184") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "manufacture_order", constraintName: "FK_fxjve79gdjfjx9bgf5vnldylo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-185") {
		addForeignKeyConstraint(baseColumnNames: "customer_order_det_id", baseTableName: "manufacture_order_change_order", constraintName: "FK_tnhid0nst40ciwfppf7nbufwd", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer_order_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-186") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "manufacture_order_change_order", constraintName: "FK_sm0ths3m32vl25nomey9ddhvs", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-187") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "manufacture_order_change_order", constraintName: "FK_rdurbq1re549w8jqhb1dd5poq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-188") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "manufacture_order_change_order", constraintName: "FK_rm96748d4k51nlbv0hb3uwl6x", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-189") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "manufacture_order_change_order", constraintName: "FK_sfsen1p67yre6ussc6jqed36r", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-190") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "manufacture_order_change_order", constraintName: "FK_t4b0hfqylg5mlnomysfrj73fl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-191") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "manufacture_order_change_order", constraintName: "FK_31127iice3a6wwa8xs7qug9e8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-192") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "manufacture_order_change_order", constraintName: "FK_rb3dprm6yho8g47np040lm89f", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-193") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "manufacture_order_det", constraintName: "FK_m1syhxx05gg50nfiwtx9muq03", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-194") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "manufacture_order_det", constraintName: "FK_cvsik3npcnxrty6er9hxghpbk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-195") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "manufacture_order_det", constraintName: "FK_8aj5wbrh9pdnv5hyqqphkuydb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-196") {
		addForeignKeyConstraint(baseColumnNames: "operation_id", baseTableName: "manufacture_order_det", constraintName: "FK_soo4ed0way3rusa54qwywy933", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-197") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "manufacture_order_det", constraintName: "FK_7ke59f13fpg255w8bkanxf1oa", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-198") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "manufacture_order_det", constraintName: "FK_gvabfa0eil0guyc869y5w7cyj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-199") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "manufacture_order_det", constraintName: "FK_1jlmrg9lcsldsdygcj0yjrwnh", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-200") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "manufacture_order_det", constraintName: "FK_4ca84505chi4den7hi133hvf", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-201") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "manufacturer", constraintName: "FK_fv414uryske23wuuilug0ss8v", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-202") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "manufacturer", constraintName: "FK_lggiwe6v1329eb10hf6gmm53n", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-203") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "material_return_sheet", constraintName: "FK_eg8dnbjl0chnxp0eoirgd7xp8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-204") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "material_return_sheet", constraintName: "FK_k4g1va335840ymps6qix8tedr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-205") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "material_return_sheet", constraintName: "FK_9hyoqt23ae0sye7245dgxngsv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-206") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "material_return_sheet", constraintName: "FK_eooivid4thixyc8aav96b1a92", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-207") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "material_return_sheet", constraintName: "FK_eateg4slsbsoemla2bf1qxiex", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-208") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "material_return_sheet_det", constraintName: "FK_4lo1s5th5aaxwj5v420l39j9s", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-209") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "material_return_sheet_det", constraintName: "FK_ld8dglwc44x82gycoro22ctlp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "material_return_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-210") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "material_return_sheet_det", constraintName: "FK_b7najufn1tpoulwpyug33682i", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-211") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "material_return_sheet_det", constraintName: "FK_p7hk5x18jl0ots1e03obyop7f", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-212") {
		addForeignKeyConstraint(baseColumnNames: "material_sheet_det_id", baseTableName: "material_return_sheet_det", constraintName: "FK_l7tq87qxyu1drkn0asmy12qyf", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "material_sheet_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-213") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "material_return_sheet_det", constraintName: "FK_qjlg9knetp1gyplvwuyg6xtla", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-214") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "material_return_sheet_det", constraintName: "FK_4knmq3fmudn60rq2k7prbh4up", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-215") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "material_return_sheet_det", constraintName: "FK_1wtrh98fq8709g6mwe2uje4qr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-216") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "material_return_sheet_det", constraintName: "FK_f3wxg8jj0gjgj579li81hj78d", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-217") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "material_sheet", constraintName: "FK_pevfhbn2p9erh2skito6xyeqo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-218") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "material_sheet", constraintName: "FK_70ppu235dyvyldg20xkpeqjk4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-219") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "material_sheet", constraintName: "FK_728r3cgkp0mhij23u0gtpjqjt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-220") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "material_sheet", constraintName: "FK_jreuatmv4xgh39k160kdvi65f", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-221") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "material_sheet", constraintName: "FK_76y2ug9bwh0ejdvgjof0587ag", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-222") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "material_sheet_det", constraintName: "FK_ogjqp2sonyris03oxwrr868r6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-223") {
		addForeignKeyConstraint(baseColumnNames: "batch_operation_id", baseTableName: "material_sheet_det", constraintName: "FK_9vn3i4pbcwe7dcqsb0xb455vq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch_operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-224") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "material_sheet_det", constraintName: "FK_p1vavp4yrb0rwpjlc4hbwff4b", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "material_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-225") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "material_sheet_det", constraintName: "FK_8ib3t4fqsib82w33qoa3e34hd", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-226") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "material_sheet_det", constraintName: "FK_araqosqcdwhf5yfme9wk9hao9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-227") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "material_sheet_det", constraintName: "FK_27cvqjd6l63m2yyto2yed71qy", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-228") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "material_sheet_det", constraintName: "FK_23k3is2mvetkoq15j0q5mcxil", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-229") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "material_sheet_det", constraintName: "FK_ohyueaig1aqvuyhh5k9f1p0st", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-230") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "material_sheet_det", constraintName: "FK_2id71ew7p3bj7ewsd87sg0afx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-231") {
		addForeignKeyConstraint(baseColumnNames: "item_category_layer1_id", baseTableName: "material_sheet_det_customize", constraintName: "FK_omvmn7h2bfguxswk33b9u517w", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_category_layer1", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-232") {
		addForeignKeyConstraint(baseColumnNames: "item_category_layer2_id", baseTableName: "material_sheet_det_customize", constraintName: "FK_g6u0c0vy9xr3f3w9p3r6mp1t6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item_category_layer2", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-233") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "material_sheet_det_customize", constraintName: "FK_aqu03t1y3uo90f7vyu6q964mt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-234") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "material_sheet_det_customize", constraintName: "FK_a0gruem9fk2edvmhir3avu29x", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-235") {
		addForeignKeyConstraint(baseColumnNames: "material_sheet_det_customize_id", baseTableName: "material_sheet_det_customize_det", constraintName: "FK_ey0jtloloubutthulvmf8xvwy", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "material_sheet_det_customize", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-236") {
		addForeignKeyConstraint(baseColumnNames: "material_sheet_det_id", baseTableName: "material_sheet_det_customize_det", constraintName: "FK_hwj99ye9lnbrt1ap6vgmu0s0m", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "material_sheet_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-237") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "material_sheet_det_customize_det", constraintName: "FK_2ibdnrenn7qt7ifo88btkxpve", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-238") {
		addForeignKeyConstraint(baseColumnNames: "operation_category_layer1_id", baseTableName: "operation", constraintName: "FK_t37g9snkens7yvuq9p1d29jrc", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation_category_layer1", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-239") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "operation", constraintName: "FK_l92bc2xurux48mw2ps0ixfe8c", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-240") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "operation_category_layer1", constraintName: "FK_7db3ihw77ylnv5a48gq8evtn7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-241") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "out_src_purchase_return_sheet", constraintName: "FK_ks8kpya7a0w5qrf83g59byosu", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-242") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "out_src_purchase_return_sheet", constraintName: "FK_ltemmn0tudmrffrr4782tskb9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-243") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "out_src_purchase_return_sheet", constraintName: "FK_lbue58awslloym9xoljnmwv8v", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-244") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "out_src_purchase_return_sheet", constraintName: "FK_2icwyhlyb8lam31g52m8f2s6g", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-245") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FK_7f4pb1802730lpekfflsau9bj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-246") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FK_rjk2abcxouu2l5ohege4kaj78", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "out_src_purchase_return_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-247") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FK_6rdlv59c8awwj5n6g5li1avnq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-248") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FK_gedybypg4ol2ialhballg3tb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-249") {
		addForeignKeyConstraint(baseColumnNames: "out_src_purchase_sheet_det_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FK_d2qq1wbfxb4p1gv74s25k9lle", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "out_src_purchase_sheet_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-250") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FK_k6cp949nagxnom1mvwijdxjbj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-251") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FK_hganudimjlp4u5pfcoglmy90s", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-252") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FK_ptjnw2d5ammwxe6hx88qfhevo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-253") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FK_48c8d3u614mbfxpvsx3brmjwl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-254") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "out_src_purchase_sheet", constraintName: "FK_kgcm39x817bdeguhufcnl2m2j", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-255") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "out_src_purchase_sheet", constraintName: "FK_5ilokt73f2h4cfrc61tmootyt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-256") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "out_src_purchase_sheet", constraintName: "FK_2dgoc8thchrmenkgy7xymuive", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-257") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "out_src_purchase_sheet", constraintName: "FK_3j2g9t9kn4cllns4s0sphaqi9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-258") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK_bnp8v27nk6y3kl91oleovj4co", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-259") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK_6nkiryewbsd3p5sjwjdg9rf3r", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "out_src_purchase_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-260") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK_48bmlr3igv2c6ttbsjwmthd51", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-261") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK_rt8haln2yqegc3in5vbw76uw7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-262") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK_96sponget5fu9wg5q4i6v9flv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-263") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK_2hutos9sxefbgaoy4qimu7oyb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-264") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK_6x611dump1hgq67fa7l83vyan", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-265") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK_f4h940rg3q31xfp4s8v3f4rj3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-266") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "param", constraintName: "FK_15g7tef40v90spx4d106820th", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-267") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "purchase_return_sheet", constraintName: "FK_erm7h6ht7pswdg9os3p9mrwy7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-268") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "purchase_return_sheet", constraintName: "FK_fd2q4ti003nq4chrut7vb95ys", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-269") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "purchase_return_sheet", constraintName: "FK_e79hkoh5dqu1nyscghvvmn5dr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-270") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "purchase_return_sheet", constraintName: "FK_9fith25pugkqauuul08cx7kd1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-271") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK_9dah6ath8kajxm6ohslykwb78", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-272") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK_8odcr53h4wd1u75wd3gm4hn9d", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "purchase_return_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-273") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK_qnprmp2cd6oc64rl7sclxwc70", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-274") {
		addForeignKeyConstraint(baseColumnNames: "purchase_sheet_det_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK_lg1k6puu45sv67bk86b9ocpsa", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "purchase_sheet_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-275") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK_c8fivfrvqxsnr7flgcpy73i", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-276") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK_p6rmce8n5yvhwd8hvev9agf8c", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-277") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK_gq1ybig81hkii0m8menhns4ok", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-278") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK_dmx7prw1rbhdcde7l4dnqeptn", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-279") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "purchase_sheet", constraintName: "FK_88rxqca1asjjm5q8aqb6s00s8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-280") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "purchase_sheet", constraintName: "FK_42ojn3gcorf7spwa63bbpn1iu", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-281") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "purchase_sheet", constraintName: "FK_r7qbnd90dpqlewd0a5oni4dmm", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-282") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "purchase_sheet", constraintName: "FK_1r42oga09nc1fr1qhdvjkr7rw", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-283") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "purchase_sheet_det", constraintName: "FK_9poerijv9bicfso6jh7h5jkyg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-284") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "purchase_sheet_det", constraintName: "FK_pvsiqjmhpev4nf1w2ksk4i6wj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "purchase_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-285") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "purchase_sheet_det", constraintName: "FK_sghaj74l429opjmkeq96iyfj1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-286") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "purchase_sheet_det", constraintName: "FK_b9nvoe5ifvxiwwyf2575f5i4e", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-287") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "purchase_sheet_det", constraintName: "FK_845oq6erfxvtfiobyu2adr4na", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-288") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "purchase_sheet_det", constraintName: "FK_pa7qqge8k6ok67cn3034sdouh", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-289") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "purchase_sheet_det", constraintName: "FK_hdm7edt9r8811m0ccaflua0v1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-290") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "report", constraintName: "FK_i6gj27jfyup8d9l0luj2s201n", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-291") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "report", constraintName: "FK_fy5rh80u3fa7s7wnxyr4vsd3q", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-292") {
		addForeignKeyConstraint(baseColumnNames: "operation_id", baseTableName: "report_param", constraintName: "FK_fsaeq0e4bx906sok26c2rpx2t", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-293") {
		addForeignKeyConstraint(baseColumnNames: "param_id", baseTableName: "report_param", constraintName: "FK_53hm28b9vsw61gyv9clyfhrmy", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "param", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-294") {
		addForeignKeyConstraint(baseColumnNames: "report_id", baseTableName: "report_param", constraintName: "FK_8nntnio4s82tqo3nld7b6oxwt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "report", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-295") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "report_param", constraintName: "FK_njoam4qk8reinxa5qtvotvr10", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-296") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "report_param", constraintName: "FK_meok1csnvjeufd1skhkenfuvf", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-297") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "report_param", constraintName: "FK_8ugbue4eb0juouc7n0iyio4vt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-298") {
		addForeignKeyConstraint(baseColumnNames: "site_group_id", baseTableName: "role_group", constraintName: "FK_ny28jwhq73twly1uekrcbe804", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-299") {
		addForeignKeyConstraint(baseColumnNames: "role_group_id", baseTableName: "role_group_role", constraintName: "FK_cqr9g8iosupkylg4fw10paumq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-300") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "role_group_role", constraintName: "FK_dxc3snhixkg9qn3c46p6hyjli", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-301") {
		addForeignKeyConstraint(baseColumnNames: "site_group_id", baseTableName: "role_group_role", constraintName: "FK_dlrjbyj2jhsprfuoe5gbcgui4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-302") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "sale_return_sheet", constraintName: "FK_q9ncrk91oc96xcu9fgtocjaiq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-303") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "sale_return_sheet", constraintName: "FK_qpci4sh7d3bjyrxwnvb3412x5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-304") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "sale_return_sheet", constraintName: "FK_kbbgej0ewnalcxyu639tetjb7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-305") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "sale_return_sheet", constraintName: "FK_c8xoir02a5fov7mmii0r1cpiq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-306") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "sale_return_sheet_det", constraintName: "FK_nbv8j4mwsp94kbtdspw8fryc8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-307") {
		addForeignKeyConstraint(baseColumnNames: "customer_order_det_id", baseTableName: "sale_return_sheet_det", constraintName: "FK_8qu1dnylrl0uppyh5clafig8r", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer_order_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-308") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "sale_return_sheet_det", constraintName: "FK_6husryq2shk2i9w0h4jcc1wr4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sale_return_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-309") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "sale_return_sheet_det", constraintName: "FK_76lh4ld0cnr4l2ucvw3n8sboh", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-310") {
		addForeignKeyConstraint(baseColumnNames: "sale_sheet_det_id", baseTableName: "sale_return_sheet_det", constraintName: "FK_7t14hqgeapgeuoejltiogqd37", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sale_sheet_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-311") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "sale_return_sheet_det", constraintName: "FK_edm035ijwvmqa91jajciryyna", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-312") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "sale_return_sheet_det", constraintName: "FK_292o14arpg1kq827914cq2ncc", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-313") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "sale_return_sheet_det", constraintName: "FK_9ije3845f3n3fdsmshcpfocrq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-314") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "sale_return_sheet_det", constraintName: "FK_cbcy2rl8mvtukm8kos2fxua9e", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-315") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "sale_sheet", constraintName: "FK_de6sv7k0tqis1bl9oparwkfih", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-316") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "sale_sheet", constraintName: "FK_r479xiyuy9mc2kqf0sfqdwk03", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-317") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "sale_sheet", constraintName: "FK_khm1als5yqy2ukotvon7r62dw", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-318") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "sale_sheet", constraintName: "FK_5j6lrgh3mr6t4bdctu7pj5uku", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-319") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "sale_sheet_det", constraintName: "FK_tjhnq978hj8hj99djkrhc8hs5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-320") {
		addForeignKeyConstraint(baseColumnNames: "customer_order_det_id", baseTableName: "sale_sheet_det", constraintName: "FK_if5b74ugvau319fw91xnjsflx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer_order_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-321") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "sale_sheet_det", constraintName: "FK_19oiydb6st3nt2lvsphatfl03", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sale_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-322") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "sale_sheet_det", constraintName: "FK_oioqwy7ohchlnl0m3d86aij62", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-323") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "sale_sheet_det", constraintName: "FK_984k2sm9u5n91og2ojekrv4rt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-324") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "sale_sheet_det", constraintName: "FK_b9qncquulkfjra6escotau1ym", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-325") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "sale_sheet_det", constraintName: "FK_krmrobivnaqafhcwpm30vj5c7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-326") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "sale_sheet_det", constraintName: "FK_efi9nxpexdmlw904dfp2kegqf", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-327") {
		addForeignKeyConstraint(baseColumnNames: "site_group_id", baseTableName: "site", constraintName: "FK_6shh0pw8icn4sc7s3usp1ph59", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-328") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "stock_in_sheet", constraintName: "FK_qf95nflime2762babrcr9bp9e", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-329") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "stock_in_sheet", constraintName: "FK_mgmdbg5383orrwqycc79mc544", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-330") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "stock_in_sheet", constraintName: "FK_m5nfkmsderpiex0ua0pg8uo1e", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-331") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "stock_in_sheet", constraintName: "FK_p95iorag6x2x81tmne0typ6xg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-332") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "stock_in_sheet_det", constraintName: "FK_2nqqu1je6cb3dniwx37fvjwfw", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-333") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "stock_in_sheet_det", constraintName: "FK_b4rnggdi89dok3sjtpruk4hx1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "stock_in_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-334") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "stock_in_sheet_det", constraintName: "FK_3eyjeo2traxomkfhamefy19ir", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-335") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "stock_in_sheet_det", constraintName: "FK_fffmvuc6jpykc4is5nk47fyqk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-336") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "stock_in_sheet_det", constraintName: "FK_tp5711hqog6ea8harif89jj68", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-337") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "stock_in_sheet_det", constraintName: "FK_b8tj4bctiy5a29yh7m0nqh8fl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-338") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "stock_in_sheet_det", constraintName: "FK_tdqsevurpl949qt8gucioc1qt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-339") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "stock_in_sheet_det", constraintName: "FK_huaa3a9cgsh5w9dajby89ivam", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-340") {
		addForeignKeyConstraint(baseColumnNames: "manufacturer_id", baseTableName: "supplier", constraintName: "FK_28blf3skobr5hc2f07ve2vqyo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacturer", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-341") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "supplier", constraintName: "FK_s33bm3bnb1q0g0t2j0sjo3odm", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-342") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "transfer_order", constraintName: "FK_3x6gfe5d8llhtj87jll18upr1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-343") {
		addForeignKeyConstraint(baseColumnNames: "in_supplier_id", baseTableName: "transfer_order", constraintName: "FK_93f7nst7m264s6ypahcfim78b", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-344") {
		addForeignKeyConstraint(baseColumnNames: "in_warehouse_id", baseTableName: "transfer_order", constraintName: "FK_oih0j2p6uip1at9vhi080fr00", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-345") {
		addForeignKeyConstraint(baseColumnNames: "in_warehouse_location_id", baseTableName: "transfer_order", constraintName: "FK_bi9e4m30jfdjyw71mv98csrre", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-346") {
		addForeignKeyConstraint(baseColumnNames: "in_workstation_id", baseTableName: "transfer_order", constraintName: "FK_d0xfmj6cc54aoah4lwflnxd41", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-347") {
		addForeignKeyConstraint(baseColumnNames: "out_supplier_id", baseTableName: "transfer_order", constraintName: "FK_9d2cu52sphx3vpxy9vlqv3tmw", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-348") {
		addForeignKeyConstraint(baseColumnNames: "out_warehouse_id", baseTableName: "transfer_order", constraintName: "FK_jn01trs9t1snkw57f1tlgbyof", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-349") {
		addForeignKeyConstraint(baseColumnNames: "out_warehouse_location_id", baseTableName: "transfer_order", constraintName: "FK_1jsr043d7562dqgs87vp6t5c1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-350") {
		addForeignKeyConstraint(baseColumnNames: "out_workstation_id", baseTableName: "transfer_order", constraintName: "FK_9rn3rvqoi78o4gkd8sq18g417", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-351") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "transfer_order", constraintName: "FK_htcmysq6ny7gsccfjkajc1cla", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-352") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "transfer_order", constraintName: "FK_8ytji4iwf6tswi8v9d5nxdnji", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-353") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "transfer_order_det", constraintName: "FK_185rqqn54archxkfyr3abhjjv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-354") {
		addForeignKeyConstraint(baseColumnNames: "header_id", baseTableName: "transfer_order_det", constraintName: "FK_cudf8v7qs8jvdiq7ktnsdohyo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "transfer_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-355") {
		addForeignKeyConstraint(baseColumnNames: "in_operation_id", baseTableName: "transfer_order_det", constraintName: "FK_orxfexhn7xt8ywsf436ixjfs4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-356") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "transfer_order_det", constraintName: "FK_a3j9k0ibyoth0dek47tofaf8n", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-357") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "transfer_order_det", constraintName: "FK_2m66bl5ao1j5bqqhbs3eejmtp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-358") {
		addForeignKeyConstraint(baseColumnNames: "out_operation_id", baseTableName: "transfer_order_det", constraintName: "FK_rorw5b84ifebf0sq4261pk3m2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-359") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "transfer_order_det", constraintName: "FK_cscxmn0dk91fpdycbtpoa24se", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-360") {
		addForeignKeyConstraint(baseColumnNames: "type_name_id", baseTableName: "transfer_order_det", constraintName: "FK_m014m5tr9ile05omqjnl8dsab", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "type_name", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-361") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "type_name", constraintName: "FK_bnkxr4t361adppyxq7w7piwl3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-362") {
		addForeignKeyConstraint(baseColumnNames: "last_login_site_id", baseTableName: "user", constraintName: "FK_a0yvgetnjbcbxq2alu33ihau3", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-363") {
		addForeignKeyConstraint(baseColumnNames: "last_login_workstation_id", baseTableName: "user", constraintName: "FK_qv1d8l5cvc8obnt91ggpsx3j7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-364") {
		addForeignKeyConstraint(baseColumnNames: "site_group_id", baseTableName: "user", constraintName: "FK_4jxy1p8w6n5qakg4kmpsvhb2v", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-365") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK_it77eq964jhfqtu54081ebtio", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-366") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK_apcc8lxk2xnug8377fatvbn04", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-367") {
		addForeignKeyConstraint(baseColumnNames: "role_group_id", baseTableName: "user_role_group", constraintName: "FK_9se04wabb1vif6yjcfged3s2p", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-368") {
		addForeignKeyConstraint(baseColumnNames: "site_group_id", baseTableName: "user_role_group", constraintName: "FK_js6ws9c0uls1unc8sckl9d692", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site_group", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-369") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role_group", constraintName: "FK_d9ttuc99ggo3nld2pd96x6pe7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-370") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "user_site", constraintName: "FK_qggpk5dbgt4qtyjs8la22js9k", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-371") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_site", constraintName: "FK_2hoesr99ded12jw9o5foy7j63", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-372") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "warehouse", constraintName: "FK_sfv52kwxoefym205pw8elwqw9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-373") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "warehouse", constraintName: "FK_jlc37t4s9vxopreicapjjresm", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-374") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "warehouse_location", constraintName: "FK_7nvsdwcb3ls2646cv215o7l6a", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-375") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "warehouse_location", constraintName: "FK_m11dap7qewyk9o6hvl24493qu", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-376") {
		addForeignKeyConstraint(baseColumnNames: "factory_id", baseTableName: "workstation", constraintName: "FK_f99gjgjacu2a881ir1konfsqy", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "factory", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-377") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "workstation", constraintName: "FK_gev69fm9u03xor4390poc1nje", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-378") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "workstation_realtime_record", constraintName: "FK_ns24iqwteq4epceaey2fs1gle", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-379") {
		addForeignKeyConstraint(baseColumnNames: "batch_operation_id", baseTableName: "workstation_realtime_record", constraintName: "FK_m01inkv537034ux476rsbi4of", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch_operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-380") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "workstation_realtime_record", constraintName: "FK_faby91a81bo8whty4wpv29h1k", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1524853372739-381") {
		addForeignKeyConstraint(baseColumnNames: "transfer_order_det_id", baseTableName: "workstation_realtime_record", constraintName: "FK_47e03t7r3t8np1ibeujv18wel", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "transfer_order_det", referencesUniqueColumn: "false")
	}
}
