export interface MetricValuesTableProps {
  metricValues: {
    id: number
    value: string
    recordedDate: string
    metricType: { name: string; type: string }
  }[]
  onDelete: (id: number) => void
}

export interface MetricTypeDropdownProps {
  metricTypes: { id: number; name: string }[]
}
