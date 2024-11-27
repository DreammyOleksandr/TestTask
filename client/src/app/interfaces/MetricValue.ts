import { MetricType } from './MetricType'

export interface MetricValue {
  id: number
  value: string
  metricType: MetricType
  recordedDate: string
  relatedMetricValues: MetricValue[]
}
