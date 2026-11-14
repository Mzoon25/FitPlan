# FitPlan Activity Catalog

The catalog is a static list in Kotlin. Each PlanItem.activityCode matches a code in this list.

| Code | Name | Allowed levels | Estimated kcal per minute |
| --- | --- | --- | ---: |
| BRISK_WALK | Brisk Walk | Beginner, Intermediate, Advanced | 4.5 |
| STAIR_CLIMB | Stair Climb | Beginner, Intermediate, Advanced | 7.0 |
| JOG | Jog | Intermediate, Advanced | 8.0 |
| CARDIO_CIRCUIT | Cardio Circuit | Advanced | 9.0 |

FitPlan uses fixed calorie estimates per minute for plan generation. Actual calorie burn varies by person and activity intensity.
