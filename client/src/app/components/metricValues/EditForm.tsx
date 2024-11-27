import { useState, useEffect } from 'react'
import { fetchMetricValueById, fetchMetricValues } from '../../utils/api'
import { MetricValue } from '@/app/interfaces/MetricValue'

interface EditMetricValueFormProps {
  id: number
}

const EditMetricValueForm: React.FC<EditMetricValueFormProps> = ({ id }) => {
  const [metricValue, setMetricValue] = useState<MetricValue | null>(null)
  const [metricValues, setMetricValues] = useState<MetricValue[]>([])
  const [selectedRelations, setSelectedRelations] = useState<number[]>([])
  const [value, setValue] = useState<string>('')

  useEffect(() => {
    const fetchData = async () => {
      const fetchedMetricValue = await fetchMetricValueById(id)
      if (fetchedMetricValue) {
        setMetricValue(fetchedMetricValue)
        setValue(fetchedMetricValue.value)
        setSelectedRelations(
          fetchedMetricValue.relatedMetricValues.map((rel: MetricValue) => rel.id)
        )
      }
      const allRelations = await fetchMetricValues()
      setMetricValues(allRelations)
    }
    fetchData()
  }, [id])

  const handleCheckboxChange = (relationId: number) => {
    setSelectedRelations((prev) =>
      prev.includes(relationId) ? prev.filter((id) => id !== relationId) : [...prev, relationId]
    )
  }

  return (
    <>
      {metricValue ? (
        <form>
          <div className='mb-3'>
            <label className='form-label'>Value</label>
            <input
              type='text'
              className='form-control'
              value={value}
              onChange={(e) => setValue(e.target.value)}
              required
            />
          </div>
          <div className='mb-3'>
            <label className='form-label'>Relations</label>
            <table className='table table-striped'>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Value</th>
                  <th>Recorded Date</th>
                  <th>Include</th>
                </tr>
              </thead>
              <tbody>
                {metricValues
                  .filter((rel) => rel.metricType.id !== metricValue.metricType.id)
                  .map(
                    (
                      relation: MetricValue,
                      index // Explicitly type 'relation' here
                    ) => (
                      <tr key={relation.id}>
                        <th scope='row'>{index + 1}</th>
                        <td>{relation.value}</td>
                        <td>{new Date(relation.recordedDate).toLocaleString()}</td>
                        <td>
                          <input
                            type='checkbox'
                            className='form-check-input'
                            checked={selectedRelations.includes(relation.id)}
                            onChange={() => handleCheckboxChange(relation.id)}
                          />
                        </td>
                      </tr>
                    )
                  )}
              </tbody>
            </table>
          </div>
        </form>
      ) : (
        <p>Loading...</p>
      )}
    </>
  )
}

export default EditMetricValueForm
