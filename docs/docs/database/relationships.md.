# Relationships

FitPlan database (Room). Names match the M2 Stage 2 file (B2.5).

| Relationship | Cardinality | Key / link | Delete rule |
|---|---|---|---|
| DailyRecord → PlanItem | One-to-many (1 : N) | PlanItem.dailyRecordId → DailyRecord.id (foreign key, NOT NULL) | ON DELETE CASCADE: deleting a daily record deletes its plan items |
| PlanItem → ActivityCatalog | Many-to-one (N : 1) | PlanItem.activityCode matches the catalog code (logical link, no foreign key) | Not applicable (the catalog is a static list in code) |
| DailyRecord ↔ Settings | No database link | Settings (DataStore) only give the default inputs; each past day keeps its own snapshot | Not applicable |

## Notes

- One daily record has many plan items, and each plan item belongs to exactly one daily record.
- `localDate` is unique, so there is only one record per day.
- Clear History deletes only the past days. With CASCADE their plan items are deleted too, and today's record and plan are kept (rule from B2.5).
